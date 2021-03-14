package it.unibo.lss.fcla.reservation.common

import java.util.Date
import java.util.UUID

/**
 * This is the interface of a Workout Reservation.
 *
 * A Consulting Reservation requires a [date] of the consulting, the [aim]
 * of the workout and the [id] of the reservation.
 */
interface WorkoutReservation {
    val aim: String
    val date: Date
    val id: UUID
}
