package it.unibo.lss.fcla.reservation.domain.entities.events.agenda

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

/**
 * An event representing a deleted workout reservation to the Agenda
 */
data class AgendaDeleteWorkoutReservation(
    override val id: UUID,
    val reservation: WorkoutReservation
) : Event
