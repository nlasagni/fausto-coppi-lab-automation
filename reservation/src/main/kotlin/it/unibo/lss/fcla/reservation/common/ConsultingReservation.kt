/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.common

import it.unibo.lss.fcla.reservation.domain.entities.reservation.FreelancerId
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * This is the interface of a Consulting Reservation.
 *
 * A Consulting Reservation requires a [date] of the consulting, the [freelancerId]
 * who will make the consulting and the [id] of the reservation.
 */
interface ConsultingReservation {
    val date: Date
    val freelancerId: FreelancerId
    val id: UUID
}
