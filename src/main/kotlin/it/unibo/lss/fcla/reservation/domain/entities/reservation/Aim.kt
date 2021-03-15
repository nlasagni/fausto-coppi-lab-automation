package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty

/**
 * Aim value object.
 *
 * It is the unique identifier of a workout [value]
 */
data class Aim(val value: String) {

    init {
        if (value.isEmpty()) {
            throw WorkoutReservationAimCannotBeEmpty()
        }
    }
}
