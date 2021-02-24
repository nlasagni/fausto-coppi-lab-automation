package it.unibo.lss.fcla.reservation.domain.exceptions

class CloseConsultingCannotBeUpdated :
    Exception("This consulting reservation is closed. You cannot modify it anymore.")
