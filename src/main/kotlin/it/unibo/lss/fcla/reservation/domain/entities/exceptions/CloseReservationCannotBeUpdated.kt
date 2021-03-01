package it.unibo.lss.fcla.reservation.domain.entities.exceptions

/**
 * [Exception] that occur when someone wants to update a close reservation
 */
class CloseReservationCannotBeUpdated :
    Exception("This consulting reservation is closed. You cannot modify it anymore.")
