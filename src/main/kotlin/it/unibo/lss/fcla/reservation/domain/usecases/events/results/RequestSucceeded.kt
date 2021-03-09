package it.unibo.lss.fcla.reservation.domain.usecases.events.results

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the success of a Consulting or Workout reservation.
 *
 * It needs the [id] of this event, the [requestId], and the [message] of the success of the event that occurred.
 */
data class RequestSucceeded(
    override val id: UUID,
    val requestId: UUID,
    val message: String = "Everything fine"
) : Event
