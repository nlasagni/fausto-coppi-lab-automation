package it.unibo.lss.fcla.reservation.domain.entities.events.agenda

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

/**
 * An event representing an added workout reservation to the Agenda
 */
data class AgendaAddWorkoutReservation(
    override val eventId: UUID,
    val reservation: WorkoutReservation
) : Event
