package it.unibo.lss.fcla.reservation.common

import java.util.UUID

/**
 * This is the interface of Event which have an [id]
 */
interface Event {
    val id: UUID
}
