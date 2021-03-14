package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the closure of a consulting reservation.
 *
 * It needs the [eventId] of this event, a [reservationToCloseId], and the [memberId]
 */
data class CloseConsultingReservationRequest(
    override val eventId: UUID,
    val reservationToCloseId: UUID,
    val memberId: UUID
) : Event
