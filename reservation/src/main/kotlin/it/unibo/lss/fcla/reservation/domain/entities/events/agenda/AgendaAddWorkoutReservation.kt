/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

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
