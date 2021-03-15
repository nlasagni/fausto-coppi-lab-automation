package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty

/**
 * Aim value object.
 *
 * It is the unique identifier of a workout [aim]
 */
data class Aim(val aim: String) {

    /**
     * check invariant
     */
    init {
        if (aim.isEmpty()) {
            throw WorkoutReservationAimCannotBeEmpty()
        }
    }
}
