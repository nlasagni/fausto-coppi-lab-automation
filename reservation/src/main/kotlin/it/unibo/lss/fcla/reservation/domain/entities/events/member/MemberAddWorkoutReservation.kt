package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

/**
 * An event representing an added workout reservation
 */
data class MemberAddWorkoutReservation(
    override val eventId: UUID,
    val reservation: WorkoutReservation
) : Event