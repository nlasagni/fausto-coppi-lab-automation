package it.unibo.lss.fcla.reservation.application.interfaces

import it.unibo.lss.fcla.reservation.presentation.Request
import it.unibo.lss.fcla.reservation.presentation.Result

/**
 * Interface to handle the conversion logic from and to string
 */
interface Presenter {

    /**
     * convert a [request] string into the corresponding [Request]
     */
    fun convertRequest(request: String): Request

    /**
     * convert a [Result] into the corresponding [String]
     */
    fun convertResult(result: Result): String
}
