/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.controller

import it.unibo.lss.fcla.athletictraining.usecase.fclat13.CheckWorkoutsInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat13.CheckWorkoutsRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat4.ScheduleWorkoutInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat4.ScheduleWorkoutRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat5.RescheduleWorkoutInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat5.RescheduleWorkoutRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat6.CancelScheduledWorkoutInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat6.CancelScheduledWorkoutRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat7.BuildWorkoutInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat7.BuildWorkoutRequest

/**
 * A [ControllerInput] that executes only [ControllerRequest] related
 * to workouts.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class WorkoutController(
    private val scheduleWorkoutInput: ScheduleWorkoutInput,
    private val rescheduleWorkoutInput: RescheduleWorkoutInput,
    private val cancelScheduledWorkoutInput: CancelScheduledWorkoutInput,
    private val buildWorkoutInput: BuildWorkoutInput,
    private val checkWorkoutsInput: CheckWorkoutsInput
) : ControllerInput {

    override fun executeRequest(request: ControllerRequest) {
        when (request) {
            is ScheduleWorkoutControllerRequest -> scheduleWorkoutInput.execute(
                ScheduleWorkoutRequest(
                    request.trainingId,
                    request.workoutId,
                    request.day,
                    request.time
                )
            )
            is RescheduleWorkoutControllerRequest -> rescheduleWorkoutInput.execute(
                RescheduleWorkoutRequest(
                    request.trainingId,
                    request.workoutId,
                    request.currentDay,
                    request.currentTime,
                    request.newDay,
                    request.newTime
                )
            )
            is CancelScheduleControllerRequest -> cancelScheduledWorkoutInput.execute(
                CancelScheduledWorkoutRequest(
                    request.trainingId,
                    request.workoutId,
                    request.day,
                    request.time
                )
            )
            is BuildWorkoutControllerRequest -> buildWorkoutInput.execute(
                BuildWorkoutRequest(
                    request.name
                )
            )
            is ListOfWorkoutsControllerRequest -> checkWorkoutsInput.execute(
                CheckWorkoutsRequest()
            )
        }
    }
}
