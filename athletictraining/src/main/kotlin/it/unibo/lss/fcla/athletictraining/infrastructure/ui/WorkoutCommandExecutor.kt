/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import it.unibo.lss.fcla.athletictraining.adapter.controller.BuildWorkoutControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.CancelScheduleControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerInput
import it.unibo.lss.fcla.athletictraining.adapter.controller.ListOfWorkoutsControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.RescheduleWorkoutControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ScheduleWorkoutControllerRequest

/**
 * Class that executes [ConsoleUtil.Commands] related to workouts.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class WorkoutCommandExecutor(private val controllerInput: ControllerInput) {

    fun isAbleToExecute(input: Int): Boolean {
        return when (input) {
            ConsoleUtil.Commands.workoutListCommand,
            ConsoleUtil.Commands.scheduleWorkoutCommand,
            ConsoleUtil.Commands.rescheduleWorkoutCommand,
            ConsoleUtil.Commands.cancelScheduleCommand,
            ConsoleUtil.Commands.buildWorkoutCommand, -> true
            else -> false
        }
    }

    fun executeCommand(input: Int) {
        when (input) {
            ConsoleUtil.Commands.workoutListCommand -> requestListOfWorkouts()
            ConsoleUtil.Commands.scheduleWorkoutCommand -> scheduleWorkout()
            ConsoleUtil.Commands.rescheduleWorkoutCommand -> rescheduleWorkout()
            ConsoleUtil.Commands.cancelScheduleCommand -> cancelSchedule()
            ConsoleUtil.Commands.buildWorkoutCommand -> buildWorkout()
        }
    }

    private fun requestListOfWorkouts() {
        controllerInput.executeRequest(ListOfWorkoutsControllerRequest())
    }

    private fun scheduleWorkout() {
        val trainingId = ConsoleUtil.requestStringField("Insert the training identifier")
        val workoutId = ConsoleUtil.requestStringField("Insert the workout identifier")
        val day = ConsoleUtil.requestDateField("Insert the day of the workout schedule")
        val time = ConsoleUtil.requestTimeField("Insert the time of the workout schedule")
        controllerInput.executeRequest(
            ScheduleWorkoutControllerRequest(
                trainingId,
                workoutId,
                day,
                time
            )
        )
    }

    private fun rescheduleWorkout() {
        val trainingId = ConsoleUtil.requestStringField("Insert the training identifier")
        val workoutId = ConsoleUtil.requestStringField("Insert the workout identifier")
        val day = ConsoleUtil.requestDateField("Insert the current day of the workout schedule")
        val time = ConsoleUtil.requestTimeField("Insert the current time of the workout schedule")
        val newDay = ConsoleUtil.requestDateField("Insert the new day of the workout schedule")
        val newTime = ConsoleUtil.requestTimeField("Insert the new time of the workout schedule")
        controllerInput.executeRequest(
            RescheduleWorkoutControllerRequest(
                trainingId,
                workoutId,
                day,
                time,
                newDay,
                newTime
            )
        )
    }

    private fun cancelSchedule() {
        val trainingId = ConsoleUtil.requestStringField("Insert the training identifier")
        val workoutId = ConsoleUtil.requestStringField("Insert the workout identifier")
        val day = ConsoleUtil.requestDateField("Insert the day of the workout schedule to be canceled")
        val time = ConsoleUtil.requestTimeField("Insert the time of the workout schedule to be canceled")
        controllerInput.executeRequest(
            CancelScheduleControllerRequest(
                trainingId,
                workoutId,
                day,
                time
            )
        )
    }

    private fun buildWorkout() {
        val workoutName = ConsoleUtil.requestStringField("Insert the workout name")
        controllerInput.executeRequest(
            BuildWorkoutControllerRequest(workoutName)
        )
    }
}
