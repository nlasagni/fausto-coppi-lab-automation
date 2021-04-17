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
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * An event representing the deletion of a consulting reservation.
 *
 * It needs the [eventId] of this event, the [reservationToDeleteId] of the consulting reservation
 * a Member wants to delete and the [memberId] of the Member whose consultation is to be deleted.
 */
data class DeleteConsultingReservationRequest(
    override val eventId: UUID,
    val reservationToDeleteId: UUID,
    val memberId: UUID
) : Event
