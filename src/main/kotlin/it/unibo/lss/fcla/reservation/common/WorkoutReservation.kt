package it.unibo.lss.fcla.reservation.common

import java.util.Date

interface WorkoutReservation {
    val aim: String
    val date: Date

    fun updateWorkoutReservationDate(date: Date): WorkoutReservation
}
