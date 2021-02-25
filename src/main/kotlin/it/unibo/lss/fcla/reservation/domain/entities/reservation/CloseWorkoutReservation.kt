package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.NoEmptyAimInWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.NoUpdateToAClosedReservation
import java.util.Date

class CloseWorkoutReservation(override val aim: String, override val date: Date) : WorkoutReservation {

    init {
        if (aim.isEmpty()) throw NoEmptyAimInWorkoutReservation()
    }

    override fun updateWorkoutReservationDate(date: Date): WorkoutReservation {
        throw NoUpdateToAClosedReservation()
    }
}
