/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat7

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Builds a new workout, refers to requirement FCLAT-7.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat7BuildWorkout(
    useCaseOutput: BuildWorkoutOutput,
    private val idGenerator: IdGenerator,
    private val workoutRepository: WorkoutRepository
) : BuildWorkoutInput,
    AthleticTrainingManagement<BuildWorkoutRequest, Workout>(useCaseOutput) {

    override fun processRequest(request: BuildWorkoutRequest): Workout {
        val workout = Workout(WorkoutId(idGenerator.generate()), request.name)
        workoutRepository.add(workout.snapshot())
        return workout
    }
}
