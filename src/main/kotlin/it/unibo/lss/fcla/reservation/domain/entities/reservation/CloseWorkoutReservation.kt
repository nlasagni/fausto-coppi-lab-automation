package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import java.util.Date
import java.util.UUID

/**
 * It is referred to a workout reservation that cannot be updated anymore
 */
class CloseWorkoutReservation(
    private val aim_: String,
    override val date: Date,
    override val id: UUID
) : WorkoutReservation {

    override val aim: Aim = Aim(aim_)

    override fun toString(): String = "Reservation workout {$id} with aim: $aim_ in date $date"

    override fun equals(other: Any?): Boolean {
        return (other is CloseWorkoutReservation) && other.id == this.id
    }

    override fun hashCode(): Int {
        var result = aim.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
