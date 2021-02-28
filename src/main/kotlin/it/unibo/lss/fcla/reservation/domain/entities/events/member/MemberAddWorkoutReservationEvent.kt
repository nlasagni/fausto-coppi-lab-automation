package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

data class MemberAddWorkoutReservationEvent(
    override val id: UUID,
    val reservation: WorkoutReservation
) : Event
