package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty

data class Aim(val aim: String) {
    init {
        if (aim.isEmpty()) {
            throw WorkoutReservationAimCannotBeEmpty()
        }
    }
}
