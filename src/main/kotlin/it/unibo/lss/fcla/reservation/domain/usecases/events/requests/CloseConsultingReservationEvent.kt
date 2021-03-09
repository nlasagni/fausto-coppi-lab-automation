package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the closure of a consulting reservation.
 *
 * It needs the [id] of this event, a [reservationId], and the [memberId]
 */
data class CloseConsultingReservationEvent(
    override val id: UUID,
    val reservationId: UUID,
    val memberId: UUID
) : Event
