/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * It is referred to a workout reservation that cannot be updated anymore
 */
class CloseWorkoutReservation(
    private val myAim: String,
    override val date: Date,
    override val id: UUID
) : WorkoutReservation {

    override val aim: Aim = Aim(myAim)

    override fun toString(): String = "Reservation workout {$id} with aim: $myAim in date $date"

    override fun equals(other: Any?): Boolean {
        return (other is CloseWorkoutReservation) && other.id == this.id
    }

    override fun hashCode(): Int {
        var result = aim.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
