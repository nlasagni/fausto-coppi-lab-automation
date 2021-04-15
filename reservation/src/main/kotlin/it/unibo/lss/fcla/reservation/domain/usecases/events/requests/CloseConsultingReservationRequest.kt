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
import java.util.UUID

/**
 * An event representing the closure of a consulting reservation.
 *
 * It needs the [eventId] of this event, a [reservationToCloseId], and the [memberId]
 */
data class CloseConsultingReservationRequest(
    override val eventId: UUID,
    val reservationToCloseId: UUID,
    val memberId: UUID
) : Event
