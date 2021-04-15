/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.agenda

import it.unibo.lss.fcla.reservation.common.WorkoutReservation

/**
 * An immutable Entity that is about the storage of workout
 * reservations inside the [workoutReservationList].
 */
class AgendaWorkoutReservation(val workoutReservationList: List<WorkoutReservation>) {

    constructor() : this(listOf<WorkoutReservation>())

    /**
     * This method is used to add a [WorkoutReservation] into the list of all member reservations
     */
    fun addWorkoutReservation(reservation: WorkoutReservation): AgendaWorkoutReservation {
        return AgendaWorkoutReservation(workoutReservationList + reservation)
    }

    /**
     * This method is used to remove a [WorkoutReservation] from the list of all member reservations
     */
    fun deleteWorkoutReservation(reservation: WorkoutReservation): AgendaWorkoutReservation {
        return AgendaWorkoutReservation(workoutReservationList - reservation)
    }
}
