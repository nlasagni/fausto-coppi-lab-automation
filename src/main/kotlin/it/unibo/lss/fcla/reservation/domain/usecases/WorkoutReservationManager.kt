package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAimEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import java.util.UUID

class WorkoutReservationManager(private var agenda: Agenda, private var ledger: MemberLedger) : Producer {

    constructor(agendaId: UUID, ledgerId: UUID, events: Map<UUID, List<Event>>) :
        this(
            computeAgenda(agendaId, events.getOrDefault(agendaId, listOf())),
            computeMemberLedger(ledgerId, events.getOrDefault(ledgerId, listOf()))
        )

    // TODO Probably useless
    constructor(agendaId: UUID, ledgerId: UUID) : this(agendaId, ledgerId, mapOf())

    private companion object {
        private fun computeAgenda(agendaId: UUID, events: List<Event>): Agenda {
            val agendaProjection = AgendaProjection(agendaId)
            return events.fold(agendaProjection.init) { state, event -> agendaProjection.update(state, event) }
        }

        private fun computeMemberLedger(ledgerId: UUID, events: List<Event>): MemberLedger {
            val ledgerProjection = MemberLedgerProjection(ledgerId)
            return events.fold(ledgerProjection.init) { state, event -> ledgerProjection.update(state, event) }
        }
    }

    private fun closeWorkoutReservation(event: CloseWorkoutReservationEvent):
        Map<UUID, List<Event>> {
            val retrievedReservation = retrieveReservation(event.reservationId)
                ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
            val closedReservation: CloseWorkoutReservation
            try {
                closedReservation = CloseWorkoutReservation(
                    retrievedReservation.aim,
                    retrievedReservation.date,
                    retrievedReservation.id
                )
            } catch (exception: WorkoutReservationAimCannotBeEmpty) {
                return errorMap(event.id, RequestFailedMessages.emptyWorkoutAim)
            }
            val agendaDeleteReservationEvent =
                AgendaDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
            val agendaAddReservationEvent = AgendaAddWorkoutReservationEvent(UUID.randomUUID(), closedReservation)
            val member = ledger.retrieveMemberWithWorkoutReservation(retrievedReservation)
            val memberDeleteReservationEvent =
                MemberDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
            val memberAddReservationEvent = MemberAddWorkoutReservationEvent(UUID.randomUUID(), closedReservation)
            return mapOf(
                agenda.id to listOf(agendaDeleteReservationEvent, agendaAddReservationEvent),
                member.id to listOf(memberDeleteReservationEvent, memberAddReservationEvent)
            )
        }

    private fun createWorkoutReservation(event: CreateWorkoutReservationEvent): Map<UUID, List<Event>> {
        val workoutReservation: OpenWorkoutReservation
        try {
            workoutReservation = OpenWorkoutReservation(
                event.aim,
                event.date,
                event.memberId
            )
        } catch (exception: WorkoutReservationAimCannotBeEmpty) {
            return errorMap(event.id, RequestFailedMessages.emptyWorkoutAim)
        } catch (exception: OpenReservationMustNotHavePastDate) {
            return errorMap(event.id, RequestFailedMessages.pastDateInReservation)
        }
        val agendaAddReservationEvent =
            AgendaAddWorkoutReservationEvent(UUID.randomUUID(), workoutReservation)
        val memberAddReservationEvent =
            MemberAddWorkoutReservationEvent(UUID.randomUUID(), workoutReservation)
        var resultMap: Map<UUID, List<Event>> = mapOf(
            agenda.id to listOf(agendaAddReservationEvent),
            event.memberId to listOf(memberAddReservationEvent)
        )
        try {
            ledger.retrieveAllMembers().first { member -> member.id == event.memberId }
        } catch (exception: NoSuchElementException) {
            resultMap = resultMap +
                (
                    ledger.id to listOf(
                        LedgerAddMemberEvent(
                            UUID.randomUUID(),
                            Member(event.firstName, event.lastName, event.memberId)
                        )
                    )
                    )
        }
        return resultMap
    }

    private fun deleteWorkoutReservation(event: DeleteWorkoutReservationEvent): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
        val agendaDeleteReservationEvent =
            AgendaDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
        val memberDeleteReservationEvent =
            MemberDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
        return mapOf(
            agenda.id to listOf(agendaDeleteReservationEvent),
            event.memberId to listOf(memberDeleteReservationEvent)
        )
    }

    private fun updateWorkoutReservation(event: UpdateWorkoutReservationEvent): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
        if (retrievedReservation is CloseWorkoutReservation) {
            return errorMap(event.id, RequestFailedMessages.noUpdateToCloseReservation)
        }
        try {
            OpenWorkoutReservation(retrievedReservation.aim, retrievedReservation.date, retrievedReservation.id)
        } catch (exception: WorkoutReservationAimCannotBeEmpty) {
            return errorMap(event.id, RequestFailedMessages.emptyWorkoutAim)
        } catch (exception: OpenReservationMustNotHavePastDate) {
            return errorMap(event.id, RequestFailedMessages.pastDateInReservation)
        }
        val reservationUpdateAimEvent = WorkoutReservationUpdateAimEvent(UUID.randomUUID(), event.aim)
        val reservationUpdateDateEvent = WorkoutReservationUpdateDateEvent(UUID.randomUUID(), event.date)
        return mapOf(event.reservationId to listOf(reservationUpdateAimEvent, reservationUpdateDateEvent))
    }

    override fun produce(event: Event): Map<UUID, List<Event>> = when (event) {
        is CloseWorkoutReservationEvent -> closeWorkoutReservation(event)
        is CreateWorkoutReservationEvent -> createWorkoutReservation(event)
        is DeleteWorkoutReservationEvent -> deleteWorkoutReservation(event)
        is UpdateWorkoutReservationEvent -> updateWorkoutReservation(event)
        else -> mapOf()
    }

    private fun errorMap(requestId: UUID, error: String): Map<UUID, List<Event>> {
        return mapOf(
            requestId to listOf(
                RequestFailedEvent(
                    UUID.randomUUID(),
                    requestId,
                    error
                )
            )
        )
    }

    private fun retrieveReservation(reservationId: UUID): WorkoutReservation? {
        return agenda.retrieveWorkoutReservation()
            .firstOrNull { workoutReservation -> reservationId == workoutReservation.id }
    }
}
