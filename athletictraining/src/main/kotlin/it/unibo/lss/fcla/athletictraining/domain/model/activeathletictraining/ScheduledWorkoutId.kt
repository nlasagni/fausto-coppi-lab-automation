/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * The unique identifier of a [ScheduledWorkout] entity.
 *
 * @property value The value of the unique identifier.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
data class ScheduledWorkoutId(
    private val workoutId: WorkoutId,
    private val schedule: Schedule
)
