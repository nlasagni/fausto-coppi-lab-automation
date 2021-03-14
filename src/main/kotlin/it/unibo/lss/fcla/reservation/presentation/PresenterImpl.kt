package it.unibo.lss.fcla.reservation.presentation

import it.unibo.lss.fcla.reservation.application.interfaces.Presenter

class PresenterImpl: Presenter {

    override fun convertRequest(request: String): Request {
        TODO()
        /*
        when (request) {
            "RequestRetrieveAgendaConsultingReservation" -> RequestRetrieveAgendaConsultingReservation()
            "RequestRetrieveAgendaWorkoutReservation" -> RequestRetrieveAgendaWorkoutReservation()
            else -> RequestRetrieveAgendaConsultingReservation()
        }
        */
    }

    override fun convertResult(result: Result): String {
        return result.toString()
    }
}
