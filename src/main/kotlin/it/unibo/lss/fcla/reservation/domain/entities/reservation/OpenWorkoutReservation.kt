package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import java.util.Date
import java.util.UUID

/**
 * It is referred to a reservation which can still be updated expressing [aim], [date] and [id]
 */
class OpenWorkoutReservation(
    override val aim: String,
    override val date: Date,
    override val id: UUID
) : WorkoutReservation {

    init {
        if (aim.isEmpty()) throw WorkoutReservationAimCannotBeEmpty()
        if (date.before(Date())) throw OpenReservationMustNotHavePastDate()
    }

    /**
     * Returns a new [OpenWorkoutReservation] after the update of the [date] of a workout reservation
     */
    fun updateWorkoutReservationDate(date: Date): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }

    /**
     * Returns a new [OpenWorkoutReservation] after the update of the [aim] of a workout reservation
     */
    fun updateWorkoutReservationAim(aim: String): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }

    override fun toString(): String = "Reservation consulting {$id} with aim: $aim in date $date"
}
