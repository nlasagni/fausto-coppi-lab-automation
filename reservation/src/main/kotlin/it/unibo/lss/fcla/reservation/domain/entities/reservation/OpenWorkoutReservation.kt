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
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.AimCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * It is referred to a reservation which can still be updated expressing [aim], [date] and [id]
 *
 * Throws [AimCannotBeEmpty] if an OpenWorkoutReservation is created without aim
 *
 * Throws [OpenReservationMustNotHavePastDate] if an OpenWorkoutReservation is created with a past date
 */
class OpenWorkoutReservation(
    private val myAim: String,
    override val date: Date,
    override val id: UUID
) : WorkoutReservation {

    override val aim: Aim = Aim(myAim)

    init {
        if (date.before(Date())) {
            throw OpenReservationMustNotHavePastDate()
        }
    }

    /**
     * Returns a new [OpenWorkoutReservation] after the update of the [date] of a workout reservation
     */
    fun updateWorkoutReservationDate(date: Date): OpenWorkoutReservation {
        return OpenWorkoutReservation(myAim, date, id)
    }

    /**
     * Returns a new [OpenWorkoutReservation] after the update of the [aim] of a workout reservation
     */
    fun updateWorkoutReservationAim(aim: String): OpenWorkoutReservation {
        return OpenWorkoutReservation(aim, date, id)
    }

    override fun toString(): String = "Reservation consulting {$id} with aim: $myAim in date $date"

    override fun equals(other: Any?): Boolean {
        return (other is OpenWorkoutReservation) && other.id == this.id
    }

    override fun hashCode(): Int {
        var result = aim.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
