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
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenWorkoutReservationProjection
import java.util.UUID

/**
 * An implementation of [Producer] that handle workout reservation
 */
class WorkoutReservationManager(
    private var agenda: Agenda,
    private var ledger: MemberLedger,
    private val eventMap: Map<UUID, List<Event>>
) : Producer {

    constructor(agendaId: UUID, ledgerId: UUID, events: Map<UUID, List<Event>>) :
        this(
            computeAgenda(agendaId, events.getOrDefault(agendaId, listOf())),
            computeMemberLedger(ledgerId, events.getOrDefault(ledgerId, listOf())),
            events
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

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [CloseWorkoutReservationEvent] occurs.
     */
    private fun closeWorkoutReservation(event: CloseWorkoutReservationEvent):
        Map<UUID, List<Event>> {
            val retrievedReservation = retrieveReservation(event.reservationId)
                ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
            if (retrievedReservation is CloseWorkoutReservation) {
                return errorMap(event.id, RequestFailedMessages.alreadyCloseReservation)
            }
            val updatedReservation = computeWorkoutReservation(retrievedReservation as OpenWorkoutReservation)
            val closedReservation = CloseWorkoutReservation(
                updatedReservation.aim,
                updatedReservation.date,
                updatedReservation.id
            )
            val agendaDeleteReservationEvent =
                AgendaDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
            val agendaAddReservationEvent = AgendaAddWorkoutReservationEvent(UUID.randomUUID(), closedReservation)
            if (!(
                memberOwnReservation(event.memberId, event.reservationId)
                    ?: return errorMap(event.id, RequestFailedMessages.memberNotFound)
                )
            ) {
                return errorMap(event.id, RequestFailedMessages.wrongMember)
            }
            val memberDeleteReservationEvent =
                MemberDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
            val memberAddReservationEvent = MemberAddWorkoutReservationEvent(UUID.randomUUID(), closedReservation)
            return mapOf(
                agenda.id to listOf(agendaDeleteReservationEvent, agendaAddReservationEvent),
                event.memberId to listOf(memberDeleteReservationEvent, memberAddReservationEvent),
                event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
            )
        }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [CreateWorkoutReservationEvent] occurs.
     */
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
        val resultMap: Map<UUID, List<Event>> = mapOf(
            agenda.id to listOf(agendaAddReservationEvent),
            event.memberId to listOf(memberAddReservationEvent),
            event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
        )
        return try {
            ledger.retrieveAllMembers().first { member -> member.id == event.memberId }
            resultMap
        } catch (exception: NoSuchElementException) {
            resultMap + (
                ledger.id to listOf(
                    LedgerAddMemberEvent(
                        UUID.randomUUID(),
                        Member(event.firstName, event.lastName, event.memberId)
                    )
                )
                )
        }
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [DeleteWorkoutReservationEvent] occurs.
     */
    private fun deleteWorkoutReservation(event: DeleteWorkoutReservationEvent): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
        val agendaDeleteReservationEvent =
            AgendaDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
        val memberDeleteReservationEvent =
            MemberDeleteWorkoutReservationEvent(UUID.randomUUID(), retrievedReservation)
        if (!(
            memberOwnReservation(event.memberId, event.reservationId)
                ?: return errorMap(event.id, RequestFailedMessages.memberNotFound)
            )
        ) {
            return errorMap(event.id, RequestFailedMessages.wrongMember)
        }
        return mapOf(
            agenda.id to listOf(agendaDeleteReservationEvent),
            event.memberId to listOf(memberDeleteReservationEvent),
            event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
        )
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [UpdateWorkoutReservationEvent] occurs.
     */
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
        return mapOf(
            event.reservationId to listOf(reservationUpdateAimEvent, reservationUpdateDateEvent),
            event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
        )
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate based on the specific occurred [event] which is related to the workout reservation.
     */
    override fun produce(event: Event): Map<UUID, List<Event>> = when (event) {
        is CloseWorkoutReservationEvent -> closeWorkoutReservation(event)
        is CreateWorkoutReservationEvent -> createWorkoutReservation(event)
        is DeleteWorkoutReservationEvent -> deleteWorkoutReservation(event)
        is UpdateWorkoutReservationEvent -> updateWorkoutReservation(event)
        else -> mapOf()
    }

    /**
     * Returns a [Map] with the [UUID] of the request event as key and a [List] of error
     * [Event] as value, given the [requestId] and the [error] message.
     */
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

    /**
     * Return a nullable [WorkoutReservation] given its [reservationId]
     */
    private fun retrieveReservation(reservationId: UUID): WorkoutReservation? {
        return agenda.retrieveWorkoutReservation()
            .firstOrNull { workoutReservation -> reservationId == workoutReservation.id }
    }

    /**
     * Check if the member with the given [memberId] is present and if him/her
     * is the owner of the reservation with the given [reservationId]
     * Return null if member is not found
     * Return false if the member is present but not the owner of the reservation
     * Return true if the member is present and is the owner of the reservation
     */
    private fun memberOwnReservation(memberId: UUID, reservationId: UUID): Boolean? {
        val member = ledger.retrieveAllMembers()
            .firstOrNull { member -> member.id == memberId }
            ?: return null
        return computeMember(member).retrieveWorkoutReservation()
            .any { reservation -> reservation.id == reservationId }
    }

    /**
     * compute the [Member] based on the events occurred and the given [member]
     * Return the updated [Member]
     */
    private fun computeMember(member: Member): Member {
        val memberProj = MemberProjection(member)
        return eventMap.getOrDefault(member.id, listOf())
            .fold(memberProj.init) { state, event -> memberProj.update(state, event) }
    }

    /**
     * compute the [WorkoutReservation] based on the events occurred and the given [reservation]
     * Return the updated [WorkoutReservation]
     */
    private fun computeWorkoutReservation(reservation: OpenWorkoutReservation): WorkoutReservation {
        val workoutProj = OpenWorkoutReservationProjection(reservation)
        return eventMap.getOrDefault(reservation.id, listOf())
            .fold(workoutProj.init) { state, event -> workoutProj.update(state, event) }
    }
}
