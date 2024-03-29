/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId

/**
 * A snapshot class used to expose all the information about a [Workout].
 *
 * @property name The name of the [Workout] to which this snapshot refers.
 * @property exercises The ordered [List] of exercises of the workout to which this snapshot refers.
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
data class WorkoutSnapshot(
    val id: WorkoutId,
    val name: String,
    val exercises: List<ExerciseId>
)
