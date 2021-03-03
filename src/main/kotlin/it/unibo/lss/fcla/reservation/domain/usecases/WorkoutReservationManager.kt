package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
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
            // TODO compute Agenda using projection
            return Agenda(agendaId)
        }

        private fun computeMemberLedger(ledgerId: UUID, events: List<Event>): MemberLedger {
            // TODO compute MemberLedger using projection
            return MemberLedger(ledgerId)
        }
    }

    private fun closeWorkoutReservation(event: CloseWorkoutReservationEvent): Map<UUID, List<Event>> {
        val retrievedReservation = agenda.retrieveWorkoutReservation()
            .firstOrNull { workoutReservation -> event.reservationId == workoutReservation.id }
        retrievedReservation ?: return mapOf(
            event.id to listOf(
                RequestFailedEvent(
                    UUID.randomUUID(),
                    event.id,
                    RequestFailedMessages.reservationNotFound
                )
            )
        )
        val closedReservation: CloseWorkoutReservation
        try {
            closedReservation = CloseWorkoutReservation(
                retrievedReservation.aim,
                retrievedReservation.date,
                retrievedReservation.id
            )
        } catch (exception: WorkoutReservationAimCannotBeEmpty) {
            return mapOf(
                event.id to listOf(
                    RequestFailedEvent(
                        UUID.randomUUID(),
                        event.id,
                        RequestFailedMessages.emptyWorkoutAim
                    )
                )
            )
        }
        val agendaDeleteReservationEvent = AgendaDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
        val agendaAddReservationEvent = AgendaAddWorkoutReservationEvent(UUID.randomUUID(), closedReservation)
        val member = ledger.retrieveMemberWithWorkoutReservation(retrievedReservation)
        val memberDeleteReservationEvent = MemberDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
        val memberAddReservationEvent = MemberAddWorkoutReservationEvent(UUID.randomUUID(), closedReservation)
        val ledgerAddMember = LedgerAddMemberEvent(UUID.randomUUID(), member.addWorkoutReservation(closedReservation))
        return mapOf(
            agenda.id to listOf(agendaDeleteReservationEvent, agendaAddReservationEvent),
            member.id to listOf(memberDeleteReservationEvent, memberAddReservationEvent),
            ledger.id to listOf(ledgerAddMember)
        )
    }

    private fun createWorkoutReservation(event: CreateWorkoutReservationEvent): Map<UUID, List<Event>> {
        /* TODO
        - create res
        - add newRes to agenda and member
         */
        return mapOf()
    }

    private fun deleteWorkoutReservation(event: DeleteWorkoutReservationEvent): Map<UUID, List<Event>> {
        /* TODO
        - retrieve res
        - remove res from agenda and member
         */
        return mapOf()
    }

    private fun updateWorkoutReservation(event: UpdateWorkoutReservationEvent): Map<UUID, List<Event>> {
        /* TODO
        - retrieve consulting
        - update cons
         */
        return mapOf()
    }

    override fun produce(event: Event): Map<UUID, List<Event>> = when (event) {
        is CloseWorkoutReservationEvent -> closeWorkoutReservation(event)
        is CreateWorkoutReservationEvent -> createWorkoutReservation(event)
        is DeleteWorkoutReservationEvent -> deleteWorkoutReservation(event)
        is UpdateWorkoutReservationEvent -> updateWorkoutReservation(event)
        else -> mapOf()
    }
}
