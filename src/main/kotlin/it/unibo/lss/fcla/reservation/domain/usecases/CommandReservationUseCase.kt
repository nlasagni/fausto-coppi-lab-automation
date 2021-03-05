package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import java.util.Date
import java.util.UUID

class CommandReservationUseCase(
    private val agendaId: UUID,
    private val ledgerId: UUID,
    private val events: Map<UUID, List<Event>>
) : ReservationUseCase() {

    constructor() : this(UUID.randomUUID(), UUID.randomUUID(), mapOf())

    override val eventStore: EventStore = EventStore(events)
    // Fake Id used to aggregate request event
    private val headquarterId: UUID = UUID.randomUUID()

    private fun handleRequestResult(event: Event, producer: Producer): String {
        eventStore.evolve(headquarterId, event, producer)
        when (val resultEvent = eventStore.getStream(event.id).first()) {
            is RequestSucceededEvent -> return resultEvent.message
            is RequestFailedEvent -> throw RequestFailedException(resultEvent.message)
            else -> throw RequestFailedException()
        }
    }

    fun requestCloseConsultingReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CloseConsultingReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestCloseWorkoutReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CloseWorkoutReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestCreateConsultingReservation(
        freelancer: String,
        date: Date,
        firstName: String,
        lastName: String,
        memberId: UUID
    ): String {
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
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CreateWorkoutReservationEvent(UUID.randomUUID(), aim, date, firstName, lastName, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestDeleteConsultingReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = DeleteConsultingReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestDeleteWorkoutReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = DeleteWorkoutReservationEvent(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    fun requestUpdateConsultingReservation(
        reservationId: UUID,
        freelancer: String,
        date: Date
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = UpdateConsultingReservationEvent(UUID.randomUUID(), reservationId, freelancer, date)
        return handleRequestResult(event, producer)
    }

    fun requestUpdateWorkoutReservation(
        reservationId: UUID,
        aim: String,
        date: Date
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = UpdateWorkoutReservationEvent(UUID.randomUUID(), reservationId, aim, date)
        return handleRequestResult(event, producer)
    }
}
