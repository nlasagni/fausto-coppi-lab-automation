package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing an update to a workout reservation aim
 */
data class WorkoutReservationUpdateAim(
    override val eventId: UUID,
    val aim: String
) : Event
