package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMember
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAim
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailed
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceeded
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
     * the aggregate when [CloseWorkoutReservation] occurs.
     */
    private fun closeWorkoutReservation(event: it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservation):
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
                AgendaDeleteWorkoutReservation(UUID.randomUUID(), retrievedReservation)
            val agendaAddReservationEvent = AgendaAddWorkoutReservation(UUID.randomUUID(), closedReservation)
            if (!(
                memberOwnReservation(event.memberId, event.reservationId)
                    ?: return errorMap(event.id, RequestFailedMessages.memberNotFound)
                )
            ) {
                return errorMap(event.id, RequestFailedMessages.wrongMember)
            }
            val memberDeleteReservationEvent =
                MemberDeleteWorkoutReservation(UUID.randomUUID(), retrievedReservation)
            val memberAddReservationEvent = MemberAddWorkoutReservation(UUID.randomUUID(), closedReservation)
            return mapOf(
                agenda.id to listOf(agendaDeleteReservationEvent, agendaAddReservationEvent),
                event.memberId to listOf(memberDeleteReservationEvent, memberAddReservationEvent),
                event.id to listOf(RequestSucceeded(UUID.randomUUID(), event.id))
            )
        }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [CreateWorkoutReservation] occurs.
     */
    private fun createWorkoutReservation(event: CreateWorkoutReservation): Map<UUID, List<Event>> {
        val workoutReservation: OpenWorkoutReservation
        try {
            workoutReservation = OpenWorkoutReservation(
                event.aim,
                event.date,
                UUID.randomUUID()
            )
        } catch (exception: WorkoutReservationAimCannotBeEmpty) {
            return errorMap(event.id, RequestFailedMessages.emptyWorkoutAim)
        } catch (exception: OpenReservationMustNotHavePastDate) {
            return errorMap(event.id, RequestFailedMessages.pastDateInReservation)
        }
        val agendaAddReservationEvent =
            AgendaAddWorkoutReservation(UUID.randomUUID(), workoutReservation)
        val memberAddReservationEvent =
            MemberAddWorkoutReservation(UUID.randomUUID(), workoutReservation)
        val resultMap: Map<UUID, List<Event>> = mapOf(
            agenda.id to listOf(agendaAddReservationEvent),
            event.memberId to listOf(memberAddReservationEvent),
            event.id to listOf(RequestSucceeded(UUID.randomUUID(), event.id))
        )
        return try {
            ledger.retrieveAllMembers().first { member -> member.id == event.memberId }
            resultMap
        } catch (exception: NoSuchElementException) {
            resultMap + (
                ledger.id to listOf(
                    LedgerAddMember(
                        UUID.randomUUID(),
                        Member(event.firstName, event.lastName, event.memberId)
                    )
                )
                )
        }
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [DeleteWorkoutReservation] occurs.
     */
    private fun deleteWorkoutReservation(event: DeleteWorkoutReservation): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
        val agendaDeleteReservationEvent =
            AgendaDeleteWorkoutReservation(UUID.randomUUID(), retrievedReservation)
        val memberDeleteReservationEvent =
            MemberDeleteWorkoutReservation(UUID.randomUUID(), retrievedReservation)
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
            event.id to listOf(RequestSucceeded(UUID.randomUUID(), event.id))
        )
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [UpdateWorkoutReservation] occurs.
     */
    private fun updateWorkoutReservation(event: UpdateWorkoutReservation): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorMap(event.id, RequestFailedMessages.reservationNotFound)
        if (retrievedReservation is CloseWorkoutReservation) {
            return errorMap(event.id, RequestFailedMessages.noUpdateToCloseReservation)
        }
        try {
            OpenWorkoutReservation(event.aim, event.date, event.id)
        } catch (exception: WorkoutReservationAimCannotBeEmpty) {
            return errorMap(event.id, RequestFailedMessages.emptyWorkoutAim)
        } catch (exception: OpenReservationMustNotHavePastDate) {
            return errorMap(event.id, RequestFailedMessages.pastDateInReservation)
        }
        val reservationUpdateAimEvent = WorkoutReservationUpdateAim(UUID.randomUUID(), event.aim)
        val reservationUpdateDateEvent = WorkoutReservationUpdateDate(UUID.randomUUID(), event.date)
        return mapOf(
            event.reservationId to listOf(reservationUpdateAimEvent, reservationUpdateDateEvent),
            event.id to listOf(RequestSucceeded(UUID.randomUUID(), event.id))
        )
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate based on the specific occurred [event] which is related to the workout reservation.
     */
    override fun produce(event: Event): Map<UUID, List<Event>> = when (event) {
        is it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservation -> closeWorkoutReservation(event)
        is CreateWorkoutReservation -> createWorkoutReservation(event)
        is DeleteWorkoutReservation -> deleteWorkoutReservation(event)
        is UpdateWorkoutReservation -> updateWorkoutReservation(event)
        else -> mapOf()
    }

    /**
     * Returns a [Map] with the [UUID] of the request event as key and a [List] of error
     * [Event] as value, given the [requestId] and the [error] message.
     */
    private fun errorMap(requestId: UUID, error: String): Map<UUID, List<Event>> {
        return mapOf(
            requestId to listOf(
                RequestFailed(
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
