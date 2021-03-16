package it.unibo.lss.fcla.reservation.domain.usecases

/**
 * Throws [RequestFailedException] is an error occurred during a request of a reservation
 * showing the error [message].
 */
class RequestFailedException(
    override val message: String = "The request you have done encountered a problem! Try it later"
) : Exception(message)
