package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import java.util.Date

class OpenWorkoutReservation(
    override val aim: String,
    override val date: Date,
    override val id: String
) : WorkoutReservation {

    init {
        if (aim.isEmpty()) throw WorkoutReservationAimCannotBeEmpty()
        if (date.before(Date())) throw OpenReservationMustNotHavePastDate()
    }

    override fun updateWorkoutReservationDate(date: Date): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }

    override fun updateWorkoutReservationAim(aim: String): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }
}
