package it.unibo.lss.fcla.reservation.domain.entities.exceptions

class CloseReservationCannotBeUpdated :
    Exception("This consulting reservation is closed. You cannot modify it anymore.")
