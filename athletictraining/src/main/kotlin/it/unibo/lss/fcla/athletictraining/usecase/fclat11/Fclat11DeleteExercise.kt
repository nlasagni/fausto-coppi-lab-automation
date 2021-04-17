/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat11

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ExerciseNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository

/**
 * Creates a previously created exercise, refers to requirement FCLAT-11.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat11DeleteExercise(
    useCaseOutput: DeleteExerciseOutput,
    private val repository: ExerciseRepository
) : DeleteExerciseInput,
    AthleticTrainingManagement<DeleteExerciseRequest, Boolean>(useCaseOutput) {

    override fun processRequest(request: DeleteExerciseRequest): Boolean {
        val exerciseSnapshot =
            repository.findById(ExerciseId(request.exerciseId)) ?: throw ExerciseNotFound()
        return repository.remove(exerciseSnapshot)
    }
}
