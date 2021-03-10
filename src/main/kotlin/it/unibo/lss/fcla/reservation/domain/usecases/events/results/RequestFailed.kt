package it.unibo.lss.fcla.reservation.domain.usecases.events.results

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the failure of a Consulting or Workout reservation.
 *
 * It needs the [eventId] of this event, the [requestId], and the [message] of the error occurred.
 */
data class RequestFailed(
    override val eventId: UUID,
    val requestId: UUID,
    val message: String
) : Event
