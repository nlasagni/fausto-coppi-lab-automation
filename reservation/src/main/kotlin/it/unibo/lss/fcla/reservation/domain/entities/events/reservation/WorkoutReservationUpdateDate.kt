package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * An event representing an update to a workout reservation date
 */
data class WorkoutReservationUpdateDate(
    override val eventId: UUID,
    val date: Date
) : Event
