package it.unibo.lss.fcla.reservation.presentation

import it.unibo.lss.fcla.reservation.application.interfaces.Presenter

/**
 * Implementation of [Request] interface
 *
 * This class handle the conversion logic from and to [String].
 * It is used by application layer to convert messages handled by infrastructure layer.
 *
 */
class PresenterImpl : Presenter {

    /**
     * convert a [String] into the corresponding [Request]
     */
    override fun convertRequest(request: String): Request {
        TODO()
    }

    /**
     * convert a [Result] into the corresponding [String]
     */
    override fun convertResult(result: Result): String {
        TODO()
    }
}
