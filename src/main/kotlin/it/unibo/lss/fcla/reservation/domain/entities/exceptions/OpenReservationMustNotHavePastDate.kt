package it.unibo.lss.fcla.reservation.domain.entities.exceptions

class OpenReservationMustNotHavePastDate :
    Exception("The selected date is before the previous one. Choose a future date")
