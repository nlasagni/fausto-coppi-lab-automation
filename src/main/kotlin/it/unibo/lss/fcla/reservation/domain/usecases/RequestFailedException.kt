package it.unibo.lss.fcla.reservation.domain.usecases

/**
 * Throws [RequestFailedException] is an error occurred during a request of a reservation
 * showing the error [message].
 */
class RequestFailedException(message: String = "Something really bad happened") : Exception(message)
