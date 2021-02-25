package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.CloseReservationCannotBeUpdated
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import java.util.Date

class OpenWorkoutReservation(override val aim: String, override val date: Date) : WorkoutReservation {
    
    init {
        if (aim.isEmpty()) throw WorkoutReservationAimCannotBeEmpty()
    }

    override fun updateWorkoutReservationDate(date: Date): WorkoutReservation {
        throw CloseReservationCannotBeUpdated()
    }

    override fun updateWorkoutReservationAim(aim: String): WorkoutReservation {
        throw CloseReservationCannotBeUpdated()
    }
}
