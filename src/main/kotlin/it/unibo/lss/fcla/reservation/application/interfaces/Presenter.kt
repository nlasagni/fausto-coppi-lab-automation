package it.unibo.lss.fcla.reservation.application.interfaces

import it.unibo.lss.fcla.reservation.presentation.Request
import it.unibo.lss.fcla.reservation.presentation.Result

interface Presenter {

    fun convertRequest(request: String): Request

    fun convertResult(result: Result): String
}
