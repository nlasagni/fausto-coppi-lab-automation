package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import it.unibo.lss.fcla.athletictraining.adapter.controller.BuildWorkoutControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerInput
import it.unibo.lss.fcla.athletictraining.adapter.controller.CreateExerciseControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.PlanTrainingControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.presenter.PresenterOutput
import it.unibo.lss.fcla.athletictraining.adapter.presenter.ViewModel
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import java.time.LocalDate

/**
 * The [View] component of this application. It has been introduced for demo purposes.
 * It reads commands from standard input and execute requests by mean of the [ControllerInput] port.
 * Furthermore, it is the [PresenterOutput] port implementation for presenter components.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class ConsoleView : View, PresenterOutput {

    companion object {
        private const val demoAthleticTrainerId = "1"
        private const val demoMemberId = "1"
        private const val demoGymMachineId = "1"
        private const val demoPurpose = "Athletic Preparation"
        private const val demoTrainingWeeks = 5L
        private const val demoExerciseName = "Demo Exercise"
        private const val demoExerciseDuration = 1000
        private const val demoExerciseIntensity = Intensity.NORMAL
        private const val demoExerciseDistance = 10000
        private const val demoWorkoutName = "Demo Workout"
    }

    private val commands = arrayOf(
        Pair(ConsoleUtil.Commands.trainingListCommand, "List of Active Athletic Trainings"),
        Pair(ConsoleUtil.Commands.workoutListCommand, "List of Workouts"),
        Pair(ConsoleUtil.Commands.exerciseListCommand, "List of Exercises"),
        Pair(ConsoleUtil.Commands.planTrainingCommand, "Plan Athletic Training"),
        Pair(ConsoleUtil.Commands.extendTrainingCommand, "Extend Athletic Training Period"),
        Pair(ConsoleUtil.Commands.completeTrainingCommand, "Complete Athletic Training"),
        Pair(ConsoleUtil.Commands.scheduleWorkoutCommand, "Schedule Workout"),
        Pair(ConsoleUtil.Commands.rescheduleWorkoutCommand, "Reschedule Workout"),
        Pair(ConsoleUtil.Commands.cancelScheduleCommand, "Cancel Scheduled Workout"),
        Pair(ConsoleUtil.Commands.buildWorkoutCommand, "Build Workout"),
        Pair(ConsoleUtil.Commands.chooseExerciseCommand, "Choose Exercise for Workout"),
        Pair(ConsoleUtil.Commands.removeExerciseCommand, "Remove Exercise from Workout"),
        Pair(ConsoleUtil.Commands.createExerciseCommand, "Create Exercise"),
        Pair(ConsoleUtil.Commands.deleteExerciseCommand, "Delete Exercise")
    )

    private lateinit var controllerInput: ControllerInput
    private lateinit var athleticTrainingCommandExecutor: AthleticTrainingCommandExecutor
    private lateinit var workoutCommandExecutor: WorkoutCommandExecutor
    private lateinit var exerciseCommandExecutor: ExerciseCommandExecutor
    private var stopped = false

    override fun registerController(controller: ControllerInput) {
        controllerInput = controller
        athleticTrainingCommandExecutor = AthleticTrainingCommandExecutor(controllerInput)
        workoutCommandExecutor = WorkoutCommandExecutor(controllerInput)
        exerciseCommandExecutor = ExerciseCommandExecutor(controllerInput)
    }

    override fun start() {
        println("Welcome to the AthleticTraining microservice demo version.")
        // TODO The 'demo' methods below have been introduced for demo purposes, remove them when no more needed.
        println("Filling in some example data for demo purposes...")
        println()
        planDemoTraining()
        createDemoExercises()
        buildDemoWorkout()
        println("Demo setup completed successfully.")
        println()
        printAllCommands()
        while (!stopped) {
            print("Please enter a command number, 'h' for help, or 'q' for quitting: ")
            try {
                when (val numberInput = readLine()) {
                    null -> println("No command specified.")
                    "h" -> printAllCommands()
                    "q" -> quit()
                    else -> processInput(numberInput.toInt())
                }
            } catch (ne: NumberFormatException) {
                println("Please insert a valid number.")
            }
        }
    }

    private fun planDemoTraining() {
        println("Creating example of planned training...")
        controllerInput.executeRequest(
            PlanTrainingControllerRequest(
                demoAthleticTrainerId,
                demoMemberId,
                demoPurpose,
                LocalDate.now(),
                LocalDate.now().plusWeeks(demoTrainingWeeks)
            )
        )
    }

    private fun createDemoExercises() {
        println("Creating example of exercise...")
        controllerInput.executeRequest(
            CreateExerciseControllerRequest(
                demoGymMachineId,
                demoExerciseName,
                demoExerciseDuration,
                demoExerciseDuration,
                demoExerciseIntensity,
                demoExerciseDistance
            )
        )
    }

    private fun buildDemoWorkout() {
        println("Creating example of workout...")
        controllerInput.executeRequest(
            BuildWorkoutControllerRequest(
                demoWorkoutName
            )
        )
    }

    override fun renderViewModel(viewModel: ViewModel) {
        println("Operation completed.")
        println("Result:")
        println()
        println(viewModel.content)
        println()
    }

    private fun processInput(input: Int) {
        try {
            executeCommand(input)
        } catch (viewValidationException: ViewValidationException) {
            println(
                "Aborting command $input due to the following reason: " + (viewValidationException.message ?: "Unknown")
            )
        }
    }

    private fun executeCommand(input: Int) {
        if (athleticTrainingCommandExecutor.isAbleToExecute(input)) {
            athleticTrainingCommandExecutor.executeCommand(input)
        } else if (workoutCommandExecutor.isAbleToExecute(input)) {
            workoutCommandExecutor.executeCommand(input)
        } else if (exerciseCommandExecutor.isAbleToExecute(input)) {
            exerciseCommandExecutor.executeCommand(input)
        } else {
            println("Unknown command.")
        }
    }

    private fun printAllCommands() {
        println("List of possible commands:")
        println()
        commands.forEach {
            println("${it.first}) ${it.second}")
        }
        println()
    }

    private fun quit() {
        println("Bye.")
        stopped = true
    }
}
