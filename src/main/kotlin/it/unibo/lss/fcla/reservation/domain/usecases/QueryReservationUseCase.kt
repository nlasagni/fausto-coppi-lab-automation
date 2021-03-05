package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenConsultingReservationProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenWorkoutReservationProjection
import java.util.UUID

class QueryReservationUseCase(
    private val agendaId: UUID,
    private val ledgerId: UUID,
    private val events: Map<UUID, List<Event>>
) : ReservationUseCase() {

    constructor() : this(UUID.randomUUID(), UUID.randomUUID(), mapOf())

    override val eventStore: EventStore = EventStore(events)

    private fun convertToConsultingFacades(reservations: List<ConsultingReservation>):
        List<ConsultingReservationFacade> {
            return reservations.map {
                consultingReservation ->
                when (consultingReservation) {
                    is OpenConsultingReservation -> ConsultingReservationFacade(consultingReservation)
                    is CloseConsultingReservation -> ConsultingReservationFacade(consultingReservation)
                    else -> {
                        // TODO Need tests and then remove
                        // This should not happen
                        throw RequestFailedException()
                    }
                }
            }
        }

    private fun convertToWorkoutFacades(reservations: List<WorkoutReservation>):
        List<WorkoutReservationFacade> {
            return reservations.map {
                workoutReservation ->
                when (workoutReservation) {
                    is OpenWorkoutReservation -> WorkoutReservationFacade(workoutReservation)
                    is CloseWorkoutReservation -> WorkoutReservationFacade(workoutReservation)
                    else -> {
                        // TODO Need tests and then remove
                        // This should not happen
                        throw RequestFailedException()
                    }
                }
            }
        }

    private fun convertToConsultingDateFacades(reservations: List<ConsultingReservation>):
        List<ConsultingReservationDateFacade> {
            return reservations.map {
                consultingReservation ->
                ConsultingReservationDateFacade(consultingReservation.id, consultingReservation.date)
            }
        }

    private fun convertToWorkoutDateFacades(reservations: List<WorkoutReservation>):
        List<WorkoutReservationDateFacade> {
            return reservations.map {
                workoutReservation ->
                WorkoutReservationDateFacade(workoutReservation.id, workoutReservation.date)
            }
        }

    fun retrieveAgendaConsultingReservation(): List<ConsultingReservationDateFacade> {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        return convertToConsultingDateFacades(agenda.retrieveConsultingReservation())
    }

    fun retrieveAgendaWorkoutReservation(): List<WorkoutReservationDateFacade> {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        return convertToWorkoutDateFacades(agenda.retrieveWorkoutReservation())
    }

    fun retrieveConsultingReservation(reservationId: UUID): ConsultingReservationFacade {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        when (
            val consultingReservation = agenda.retrieveConsultingReservation()
                .firstOrNull { consultingReservation -> reservationId == consultingReservation.id }
        ) {
            is OpenConsultingReservation -> {
                val openConsultingReservation = computeAggregate(
                    consultingReservation.id,
                    OpenConsultingReservationProjection(consultingReservation)
                )
                return ConsultingReservationFacade(openConsultingReservation)
            }
            is CloseConsultingReservation -> {
                return ConsultingReservationFacade(consultingReservation)
            }
            else -> {
                // TODO Need tests and then remove
                if (consultingReservation != null) {
                    // This should not happen
                    throw RequestFailedException()
                }
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }

    fun retrieveWorkoutReservation(reservationId: UUID): WorkoutReservationFacade {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        when (
            val workoutReservation = agenda.retrieveWorkoutReservation()
                .firstOrNull { workoutReservation -> reservationId == workoutReservation.id }
        ) {
            is OpenWorkoutReservation -> {
                val openWorkoutReservation = computeAggregate(
                    workoutReservation.id,
                    OpenWorkoutReservationProjection(workoutReservation)
                )
                return WorkoutReservationFacade(openWorkoutReservation)
            }
            is CloseWorkoutReservation -> {
                return WorkoutReservationFacade(workoutReservation)
            }
            else -> {
                // TODO Need tests and then remove
                if (workoutReservation != null) {
                    // This should not happen
                    throw RequestFailedException()
                }
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }

    fun retrieveMemberConsultingReservations(memberId: UUID): List<ConsultingReservationDateFacade> {
        val ledger = computeAggregate(ledgerId, MemberLedgerProjection(ledgerId))
        when (val member = ledger.retrieveAllMembers().firstOrNull { member -> member.id == memberId }) {
            is Member ->
                return convertToConsultingDateFacades(
                    computeAggregate(memberId, MemberProjection(member)).retrieveConsultingReservation()
                )
            else -> {
                // TODO Need tests and then remove
                if (member != null) {
                    // This should not happen
                    throw RequestFailedException()
                }
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }

    fun retrieveMemberWorkoutReservations(memberId: UUID): List<WorkoutReservationDateFacade> {
        val ledger = computeAggregate(ledgerId, MemberLedgerProjection(ledgerId))
        when (val member = ledger.retrieveAllMembers().firstOrNull { member -> member.id == memberId }) {
            is Member ->
                return convertToWorkoutDateFacades(
                    computeAggregate(memberId, MemberProjection(member)).retrieveWorkoutReservation()
                )
            else -> {
                // TODO Need tests and then remove
                if (member != null) {
                    // This should not happen
                    throw RequestFailedException()
                }
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }
}
