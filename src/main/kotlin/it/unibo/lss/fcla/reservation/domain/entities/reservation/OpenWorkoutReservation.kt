package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import java.util.Date
import java.util.UUID

class OpenWorkoutReservation(
    override val aim: String,
    override val date: Date,
    override val id: UUID
) : WorkoutReservation {

    init {
        if (aim.isEmpty()) throw WorkoutReservationAimCannotBeEmpty()
        if (date.before(Date())) throw OpenReservationMustNotHavePastDate()
    }

    fun updateWorkoutReservationDate(date: Date): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }

    fun updateWorkoutReservationAim(aim: String): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }
}
