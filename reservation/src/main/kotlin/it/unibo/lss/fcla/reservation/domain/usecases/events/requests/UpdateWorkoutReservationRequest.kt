/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * An event representing the update of a consulting reservation.
 *
 * It needs the [eventId] of this event, the [reservationToUpdateId] of the consulting reservation
 * a Member wants to update, the [aim] to update and the [date] to update.
 */
data class UpdateWorkoutReservationRequest(
    override val eventId: UUID,
    val reservationToUpdateId: UUID,
    val aim: String,
    val date: Date
) : Event
