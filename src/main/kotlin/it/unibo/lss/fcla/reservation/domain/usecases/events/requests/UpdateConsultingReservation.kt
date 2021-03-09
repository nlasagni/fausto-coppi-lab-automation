package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * An event representing the update of a consulting reservation.
 *
 * It needs the [id] of this event, the [reservationId] of the consulting reservation a Member wants to update,
 * the [freelancer] to update and the [date] to update.
 */
data class UpdateConsultingReservation(
    override val id: UUID,
    val reservationId: UUID,
    val freelancer: String,
    val date: Date
) : Event
