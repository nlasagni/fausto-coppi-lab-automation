package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent

class ConsultingReservationManager() : Producer {

    private fun closeConsultingReservation(): List<Event> {
        return listOf()
    }

    private fun createConsultingReservation(): List<Event> {
        return listOf()
    }

    private fun deleteConsultingReservation(): List<Event> {
        return listOf()
    }

    private fun updateConsultingReservation(): List<Event> {
        return listOf()
    }

    override fun produce(event: Event): List<Event> = when (event) {
        is CloseConsultingReservationEvent -> closeConsultingReservation()
        is CreateConsultingReservationEvent -> createConsultingReservation()
        is DeleteConsultingReservationEvent -> deleteConsultingReservation()
        is UpdateConsultingReservationEvent -> updateConsultingReservation()
        else -> listOf()
    }
}
