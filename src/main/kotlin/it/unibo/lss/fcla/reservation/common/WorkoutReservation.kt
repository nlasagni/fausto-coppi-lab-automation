package it.unibo.lss.fcla.reservation.common

import java.util.Date

interface WorkoutReservation {
    val aim: String
    val date: Date
    val id: String

    fun updateWorkoutReservationDate(date: Date): WorkoutReservation

    fun updateWorkoutReservationAim(aim: String): WorkoutReservation
}
