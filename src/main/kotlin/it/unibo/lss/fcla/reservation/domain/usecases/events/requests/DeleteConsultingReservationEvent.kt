package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the deletion of a consulting reservation.
 *
 * It needs the [id] of this event, the [reservationId] of the consulting reservation a Member wants to delete
 * and the [memberId] of the Member whose consultation is to be deleted.
 */
data class DeleteConsultingReservationEvent(
    override val id: UUID,
    val reservationId: UUID,
    val memberId: UUID
) : Event
