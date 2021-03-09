package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailed
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceeded
import java.util.Date
import java.util.UUID

/**
 * A command [ReservationUseCase] which handles the command requests
 */
class CommandReservationUseCase(
    private val agendaId: UUID,
    private val ledgerId: UUID,
    override val eventStore: EventStore
) : ReservationUseCase() {

    // Fake Id used to aggregate request event
    private val headquarterId: UUID = UUID.randomUUID()

    /**
     * Return a [String] expressing the result of a request given the occurred [event] and its [producer].
     */
    private fun handleRequestResult(event: Event, producer: Producer): String {
        eventStore.evolve(headquarterId, event, producer)
        when (val resultEvent = eventStore.getStream(event.eventId).first()) {
            is RequestSucceeded -> return resultEvent.message
            is RequestFailed -> throw RequestFailedException(resultEvent.message)
            else -> throw RequestFailedException()
        }
    }

    /**
     * Return a [String] expressing the result of the close consulting request given the
     * [reservationId] and the [memberId].
     */
    fun requestCloseConsultingReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = ConsultingReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CloseConsultingReservationRequest(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the close workout request given the
     * [reservationId] and the [memberId].
     */
    fun requestCloseWorkoutReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CloseWorkoutReservationRequest(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the create consulting request given the
     * [freelancer], the [date] of the consulting, the [firstName] of the member,
     * the [lastName] of the member and the [memberId].
     */
    fun requestCreateConsultingReservation(
        freelancer: String,
        date: Date,
        firstName: String,
        lastName: String,
        memberId: UUID
    ): String {
        val producer: Producer = ConsultingReservationManager(agendaId, ledgerId, eventStore.get())
        val event =
            CreateConsultingReservationRequest(UUID.randomUUID(), freelancer, date, firstName, lastName, memberId)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the create workout request given the
     * [aim], the [date] of the consulting, the [firstName] of the member,
     * the [lastName] of the member and the [memberId].
     */
    fun requestCreateWorkoutReservation(
        aim: String,
        date: Date,
        firstName: String,
        lastName: String,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = CreateWorkoutReservationRequest(UUID.randomUUID(), aim, date, firstName, lastName, memberId)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the delete consulting request given the
     * [reservationId] and the [memberId].
     */
    fun requestDeleteConsultingReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = ConsultingReservationManager(agendaId, ledgerId, eventStore.get())
        val event = DeleteConsultingReservationRequest(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the delete workout request given the
     * [reservationId] and the [memberId].
     */
    fun requestDeleteWorkoutReservation(
        reservationId: UUID,
        memberId: UUID
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = DeleteWorkoutReservationRequest(UUID.randomUUID(), reservationId, memberId)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the update consulting request given the
     * [reservationId],the new [freelancer] and the new [date].
     */
    fun requestUpdateConsultingReservation(
        reservationId: UUID,
        freelancer: String,
        date: Date
    ): String {
        val producer: Producer = ConsultingReservationManager(agendaId, ledgerId, eventStore.get())
        val event = UpdateConsultingReservationRequest(UUID.randomUUID(), reservationId, freelancer, date)
        return handleRequestResult(event, producer)
    }

    /**
     * Return a [String] expressing the result of the update workout request given the
     * [reservationId],the new [aim] and the new [date].
     */
    fun requestUpdateWorkoutReservation(
        reservationId: UUID,
        aim: String,
        date: Date
    ): String {
        val producer: Producer = WorkoutReservationManager(agendaId, ledgerId, eventStore.get())
        val event = UpdateWorkoutReservationRequest(UUID.randomUUID(), reservationId, aim, date)
        return handleRequestResult(event, producer)
    }
}
