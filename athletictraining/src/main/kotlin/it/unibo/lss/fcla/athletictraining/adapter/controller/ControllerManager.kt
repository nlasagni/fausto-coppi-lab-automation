/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.controller

/**
 * The main [ControllerInput] component which orchestrates all
 * the [ControllerRequest]s received.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class ControllerManager(
    private val athleticTrainingController: AthleticTrainingController,
    private val workoutController: WorkoutController,
    private val exerciseController: ExerciseController
) : ControllerInput {

    override fun executeRequest(request: ControllerRequest) {
        when {
            isAthleticTrainingRequest(request) -> athleticTrainingController.executeRequest(request)
            isWorkoutRequest(request) -> workoutController.executeRequest(request)
            isExerciseRequest(request) -> exerciseController.executeRequest(request)
        }
    }

    private fun isAthleticTrainingRequest(request: ControllerRequest): Boolean {
        return request is PlanTrainingControllerRequest ||
            request is ExtendTrainingPeriodControllerRequest ||
            request is CompleteTrainingControllerRequest ||
            request is ListOfTrainingsControllerRequest
    }

    private fun isWorkoutRequest(request: ControllerRequest): Boolean {
        return request is ScheduleWorkoutControllerRequest ||
            request is RescheduleWorkoutControllerRequest ||
            request is CancelScheduleControllerRequest ||
            request is BuildWorkoutControllerRequest ||
            request is ListOfWorkoutsControllerRequest
    }

    private fun isExerciseRequest(request: ControllerRequest): Boolean {
        return request is ChooseExerciseControllerRequest ||
            request is RemoveExerciseControllerRequest ||
            request is CreateExerciseControllerRequest ||
            request is DeleteExerciseControllerRequest ||
            request is ListOfExercisesControllerRequest
    }
}
