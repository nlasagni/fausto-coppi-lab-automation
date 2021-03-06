package it.unibo.lss.fcla.reservation.common

import java.util.Date
import java.util.UUID

interface WorkoutReservation {
    val aim: String
    val date: Date
    val id: UUID
}
