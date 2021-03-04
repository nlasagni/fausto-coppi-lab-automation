package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenConsultingReservationProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.Projection
import java.util.Date
import java.util.UUID

class CommandReservationUseCase(
        private val agendaId: UUID,
        private val ledgerId: UUID,
        private val events: Map<UUID, List<Event>>) : ReservationUseCase() {

    constructor() : this(UUID.randomUUID(),UUID.randomUUID(), mapOf())

    override val eventStore: EventStore = EventStore(events)

    fun requestCloseConsultingReservation(
            reservationId: UUID,
            memberId: UUID): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CloseConsultingReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestCloseWorkoutReservation(
            reservationId: UUID,
            memberId: UUID): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CloseWorkoutReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestCreateConsultingReservation(
        freelancer: String,
        date: Date,
        firstName: String,
        lastName: String,
        memberId: UUID): String {
        val producer: Producer = ConsultingReservationManager(agendaId, ledgerId, eventStore.get())
        val event =
            CreateConsultingReservationEvent(UUID.randomUUID(), freelancer, date, firstName, lastName, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestCreateWorkoutReservation(
        aim: String,
        date: Date,
        firstName: String,
        lastName: String,
        memberId: UUID): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CreateWorkoutReservationEvent(UUID.randomUUID(), aim, date, firstName, lastName, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestDeleteConsultingReservation(
            reservationId: UUID,
            memberId: UUID): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = DeleteConsultingReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestDeleteWorkoutReservation(
            reservationId: UUID,
            memberId: UUID): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = DeleteWorkoutReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestUpdateConsultingReservation(
            reservationId: UUID,
            freelancer: String,
            date: Date): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = UpdateConsultingReservationEvent(UUID.randomUUID(), reservationId, freelancer, date)
        return handleRequestResult(event, producer)
    }

    fun requestUpdateWorkoutReservation(
            reservationId: UUID,
            aim: String,
            date: Date): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = UpdateWorkoutReservationEvent(UUID.randomUUID(), reservationId, aim, date)
        return handleRequestResult(event, producer)
    }

    fun retrieveConsultingReservation(reservationId: UUID): ConsultingReservationFacade {
        val agenda = computeAggregate(agendaId,AgendaProjection(agendaId))
        when (val consultingReservation = agenda.retrieveConsultingReservation()
                .firstOrNull { consultingReservation -> reservationId == consultingReservation.id }) {
            is OpenConsultingReservation -> {
                val openConsultingReservation = computeAggregate(
                        consultingReservation.id,
                        OpenConsultingReservationProjection(consultingReservation))
                return ConsultingReservationFacade(
                    openConsultingReservation.date,
                    openConsultingReservation.freelancerId,
                    openConsultingReservation.id,
                    isOpen = true)
            }
            is CloseConsultingReservation -> {
                return ConsultingReservationFacade(
                    consultingReservation.date,
                    consultingReservation.freelancerId,
                    consultingReservation.id,
                    isOpen = false)
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
}
