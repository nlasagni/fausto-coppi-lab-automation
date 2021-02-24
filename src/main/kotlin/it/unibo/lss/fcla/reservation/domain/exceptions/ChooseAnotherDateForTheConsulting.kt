package it.unibo.lss.fcla.reservation.domain.exceptions

class ChooseAnotherDateForTheConsulting :
    Exception("The selected date is before the previous one. Choose a future date")
