package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent

class ConsultingReservationManager() : Producer {

    private fun closeConsultingReservation(event: CloseConsultingReservationEvent): List<Event> {
        return listOf()
    }

    private fun createConsultingReservation(event: CreateConsultingReservationEvent): List<Event> {
        return listOf()
    }

    private fun deleteConsultingReservation(event: DeleteConsultingReservationEvent): List<Event> {
        return listOf()
    }

    private fun updateConsultingReservation(event: UpdateConsultingReservationEvent): List<Event> {
        return listOf()
    }

    override fun produce(event: Event): List<Event> = when (event) {
        is CloseConsultingReservationEvent -> closeConsultingReservation(event)
        is CreateConsultingReservationEvent -> createConsultingReservation(event)
        is DeleteConsultingReservationEvent -> deleteConsultingReservation(event)
        is UpdateConsultingReservationEvent -> updateConsultingReservation(event)
        else -> listOf()
    }
}
