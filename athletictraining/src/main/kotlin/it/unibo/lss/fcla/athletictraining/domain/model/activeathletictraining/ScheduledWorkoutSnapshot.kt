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
 * A snapshot class used to expose all the relevant information about a [ScheduledWorkout].
 *
 * @property id The [ScheduledWorkoutId] to which this snapshot refers.
 * @property workout The [WorkoutId] of the [ScheduledWorkout] to which this snapshot refers.
 * @property schedule The [Schedule] of the [ScheduledWorkout] to which this snapshot refers.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
data class ScheduledWorkoutSnapshot(
    val id: ScheduledWorkoutId,
    val workout: WorkoutId,
    val schedule: Schedule
)
