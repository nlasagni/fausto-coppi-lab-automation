package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * [Event] representing an update to a workout reservation aim
 */
data class WorkoutReservationUpdateAimEvent(
    override val id: UUID,
    val aim: String
) : Event
