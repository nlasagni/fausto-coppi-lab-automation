package it.unibo.lss.fcla.reservation.domain.exceptions

class NoUpdateToAClosedReservation : Exception("A closed reservation cannot be updated")
