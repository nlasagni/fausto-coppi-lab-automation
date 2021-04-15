/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat14

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository

/**
 * Checks all the created exercises, refers to requirement FCLAT-14.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class Fclat14CheckExercises(
    useCaseOutput: CheckExercisesOutput,
    private val repository: ExerciseRepository
) : CheckExercisesInput,
    AthleticTrainingManagement<CheckExercisesRequest, Collection<Exercise>>(useCaseOutput) {

    override fun processRequest(request: CheckExercisesRequest): Collection<Exercise> {
        return repository.findAll().map { Exercise.rehydrate(it) }
    }
}
