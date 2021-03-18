package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * An event representing the update of a consulting reservation.
 *
 * It needs the [eventId] of this event, the [reservationToUpdateId] of the consulting reservation
 * a Member wants to update, the [freelancer] to update and the [date] to update.
 */
data class UpdateConsultingReservationRequest(
    override val eventId: UUID,
    val reservationToUpdateId: UUID,
    val freelancer: UUID,
    val date: Date
) : Event
