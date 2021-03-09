package it.unibo.lss.fcla.reservation.common

import java.util.Date
import java.util.UUID

/**
 * This is the interface of a Consulting Reservation.
 *
 * A Consulting Reservation requires a [date] of the consulting, the [freelancerId]
 * who will make the consulting and the [id] of the reservation.
 */
interface ConsultingReservation {
    val date: Date
    val freelancerId: String
    val id: UUID
}
