package it.unibo.lss.fcla.reservation.domain.entities.exceptions

/**
 * [Exception] that occur when a reservation is created with a past date
 */
class OpenReservationMustNotHavePastDate :
    Exception("The selected date is before the previous one. Choose a future date")
