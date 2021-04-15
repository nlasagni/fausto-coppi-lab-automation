/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * A CompletedWorkout is a workout that has been completed during an athletic training.
 *
 * A CompletedWorkout must refer to a Workout, otherwise a [WorkoutIdMissing] is thrown.
 * Furthermore it must have a valid [Schedule].
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class CompletedWorkout(
    private val workout: WorkoutId,
    private var schedule: Schedule
) {

    init {
        if (workout.value.isEmpty()) {
            throw WorkoutIdMissing()
        }
    }

    /**
     * Retrieves the [WorkoutId] of this [CompletedWorkout].
     * @return The [WorkoutId] of this [CompletedWorkout].
     */
    fun scheduledForWorkout(): WorkoutId {
        return workout
    }

    /**
     * Retrieves the [Schedule] of this [CompletedWorkout].
     * @return The [Schedule] of this [CompletedWorkout].
     */
    fun scheduledOn(): Schedule {
        return schedule
    }

    override fun toString(): String {
        return "CompletedWorkout(workout=$workout, schedule=$schedule)"
    }
}
