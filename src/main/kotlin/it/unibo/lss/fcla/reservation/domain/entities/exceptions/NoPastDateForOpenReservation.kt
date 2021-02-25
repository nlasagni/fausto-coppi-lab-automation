package it.unibo.lss.fcla.reservation.domain.entities.exceptions

class NoPastDateForOpenReservation :
    Exception("The selected date is before the previous one. Choose a future date")
