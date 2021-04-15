/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.controller

import it.unibo.lss.fcla.athletictraining.usecase.fclat10.CreateExerciseInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat10.CreateExerciseRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat11.DeleteExerciseInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat11.DeleteExerciseRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat14.CheckExercisesInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat14.CheckExercisesRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat8.ChooseExerciseForWorkoutInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat8.ChooseExerciseRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat9.RemoveExerciseFromWorkoutInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat9.RemoveExerciseRequest

/**
 * A [ControllerInput] that executes only [ControllerRequest] related
 * to exercises.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class ExerciseController(
    private val chooseExerciseForWorkoutInput: ChooseExerciseForWorkoutInput,
    private val removeExerciseFromWorkoutInput: RemoveExerciseFromWorkoutInput,
    private val createExerciseInput: CreateExerciseInput,
    private val deleteExerciseInput: DeleteExerciseInput,
    private val checkExercisesInput: CheckExercisesInput
) : ControllerInput {

    override fun executeRequest(request: ControllerRequest) {
        when (request) {
            is ChooseExerciseControllerRequest -> chooseExerciseForWorkoutInput.execute(
                ChooseExerciseRequest(
                    request.workoutId,
                    request.exerciseId
                )
            )
            is RemoveExerciseControllerRequest -> removeExerciseFromWorkoutInput.execute(
                RemoveExerciseRequest(
                    request.workoutId,
                    request.exerciseIndex
                )
            )
            is CreateExerciseControllerRequest -> createExerciseInput.execute(
                CreateExerciseRequest(
                    request.gymMachine,
                    request.name,
                    request.durationOfExecution,
                    request.durationOfRest,
                    request.intensity,
                    request.distance
                )
            )
            is DeleteExerciseControllerRequest -> deleteExerciseInput.execute(
                DeleteExerciseRequest(request.exerciseId)
            )
            is ListOfExercisesControllerRequest -> checkExercisesInput.execute(
                CheckExercisesRequest()
            )
        }
    }
}
