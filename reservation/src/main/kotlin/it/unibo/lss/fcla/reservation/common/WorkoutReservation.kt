/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.common

import it.unibo.lss.fcla.reservation.domain.entities.reservation.Aim
import java.util.Date
import java.util.UUID

/**
 * This is the interface of a Workout Reservation.
 *
 * A Consulting Reservation requires a [date] of the consulting, the [aim]
 * of the workout and the [id] of the reservation.
 */
interface WorkoutReservation {
    val aim: Aim
    val date: Date
    val id: UUID
}
