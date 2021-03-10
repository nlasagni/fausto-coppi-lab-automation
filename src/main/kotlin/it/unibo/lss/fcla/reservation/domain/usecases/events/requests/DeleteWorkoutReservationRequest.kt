package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the deletion of a workout reservation.
 *
 * It needs the [eventId] of this event, the [reservationToDeleteId] of the consulting reservation
 * a Member wants to delete and the [memberId] of the Member whose consultation is to be deleted.
 */
data class DeleteWorkoutReservationRequest(
    override val eventId: UUID,
    val reservationToDeleteId: UUID,
    val memberId: UUID
) : Event
