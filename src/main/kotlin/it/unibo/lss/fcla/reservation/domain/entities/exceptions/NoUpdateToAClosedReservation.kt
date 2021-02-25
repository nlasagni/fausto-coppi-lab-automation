package it.unibo.lss.fcla.reservation.domain.entities.exceptions

class NoUpdateToAClosedReservation : Exception("A closed reservation cannot be updated")
