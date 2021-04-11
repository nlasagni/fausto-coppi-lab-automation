package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import it.unibo.lss.fcla.athletictraining.adapter.controller.ChooseExerciseControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerInput
import it.unibo.lss.fcla.athletictraining.adapter.controller.CreateExerciseControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.DeleteExerciseControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ListOfExercisesControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.RemoveExerciseControllerRequest

/**
 * Class that executes [ConsoleUtil.Commands] related to exercises.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class ExerciseCommandExecutor(private val controllerInput: ControllerInput) {

    fun isAbleToExecute(input: Int): Boolean {
        return when (input) {
            ConsoleUtil.Commands.exerciseListCommand,
            ConsoleUtil.Commands.chooseExerciseCommand,
            ConsoleUtil.Commands.removeExerciseCommand,
            ConsoleUtil.Commands.createExerciseCommand,
            ConsoleUtil.Commands.deleteExerciseCommand, -> true
            else -> false
        }
    }

    fun executeCommand(input: Int) {
        when (input) {
            ConsoleUtil.Commands.exerciseListCommand -> requestListOfExercises()
            ConsoleUtil.Commands.chooseExerciseCommand -> chooseExercise()
            ConsoleUtil.Commands.removeExerciseCommand -> removeExercise()
            ConsoleUtil.Commands.createExerciseCommand -> createExercise()
            ConsoleUtil.Commands.deleteExerciseCommand -> deleteExercise()
            else -> println("Unknown command.")
        }
    }

    private fun requestListOfExercises() {
        controllerInput.executeRequest(ListOfExercisesControllerRequest())
    }

    private fun chooseExercise() {
        val workoutId = ConsoleUtil.requestStringField("Insert the workout identifier")
        val exerciseId = ConsoleUtil.requestStringField("Insert the exercise identifier")
        controllerInput.executeRequest(
            ChooseExerciseControllerRequest(
                workoutId,
                exerciseId
            )
        )
    }

    private fun removeExercise() {
        val workoutId = ConsoleUtil.requestStringField("Insert the workout identifier")
        val exerciseIndex = ConsoleUtil.requestIntField("Insert the exercise index of the list to be removed")
        controllerInput.executeRequest(
            RemoveExerciseControllerRequest(workoutId, exerciseIndex)
        )
    }

    private fun createExercise() {
        val gymMachineId =
            ConsoleUtil.requestStringField("Insert the gym machine identifier related to this exercise")
        val name = ConsoleUtil.requestStringField("Insert the name of the exercise")
        val durationOfExecution = ConsoleUtil.requestIntField("Insert the duration of execution (in seconds)")
        val durationOfRest = ConsoleUtil.requestIntField("Insert the duration of rest (in seconds)")
        val intensity = ConsoleUtil.requestIntField("Insert the intensity (from 1 to 10)")
        val distance = ConsoleUtil.requestIntField("Insert the distance")
        controllerInput.executeRequest(
            CreateExerciseControllerRequest(
                gymMachineId,
                name,
                durationOfExecution,
                durationOfRest,
                intensity,
                distance
            )
        )
    }

    private fun deleteExercise() {
        val exerciseId = ConsoleUtil.requestStringField("Insert the exercise identifier")
        controllerInput.executeRequest(
            DeleteExerciseControllerRequest(exerciseId)
        )
    }
}
