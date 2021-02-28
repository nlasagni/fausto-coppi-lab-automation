package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import java.util.Date
import java.util.UUID

/**
 * It is referred to a workout reservation that cannot be updated anymore
 */
class CloseWorkoutReservation(
    override val aim: String,
    override val date: Date,
    override val id: UUID
) : WorkoutReservation {

    init {
        if (aim.isEmpty()) throw WorkoutReservationAimCannotBeEmpty()
    }

    override fun toString(): String = "Reservation workout {$id} with aim: $aim in date $date"
}
