package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.exceptions.CloseConsultingCannotBeUpdated

/**
 * It is referred to a consulting that cannot be updated anymore
 */
class CloseConsultingReservation {
    fun updateDateOfConsulting(): CloseConsultingReservation {
        throw CloseConsultingCannotBeUpdated()
    }
}
