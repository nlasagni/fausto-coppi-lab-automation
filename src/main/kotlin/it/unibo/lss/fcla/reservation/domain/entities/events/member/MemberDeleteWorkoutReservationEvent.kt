package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

/**
 * [Event] representing a deleted workout reservation
 */
data class MemberDeleteWorkoutReservationEvent(
    override val id: UUID,
    val reservation: WorkoutReservation
) : Event
