package it.unibo.lss.fcla.reservation.common

import java.util.Date
import java.util.UUID

/**
 * This is the interface of a Consulting Reservation
 * @param date The date of the requested consulting
 * @param freelancerId The freelancer who will make the consulting
 */
interface ConsultingReservation {
    val date: Date
    val freelancerId: String
    val id: UUID
}
