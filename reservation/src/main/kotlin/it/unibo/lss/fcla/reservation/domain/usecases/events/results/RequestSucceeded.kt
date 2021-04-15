/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases.events.results

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing the success of a Consulting or Workout reservation.
 *
 * It needs the [eventId] of this event, the [requestId], and the [message] of the success of the event that occurred.
 */
data class RequestSucceeded(
    override val eventId: UUID,
    val requestId: UUID,
    val message: String = "The request succeeded"
) : Event
