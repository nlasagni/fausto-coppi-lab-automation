package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import it.unibo.lss.fcla.athletictraining.adapter.controller.CompleteTrainingControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerInput
import it.unibo.lss.fcla.athletictraining.adapter.controller.ExtendTrainingPeriodControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.ListOfTrainingsControllerRequest
import it.unibo.lss.fcla.athletictraining.adapter.controller.PlanTrainingControllerRequest

/**
 * @author Nicola Lasagni on 11/04/2021.
 */
class AthleticTrainingCommandExecutor(private val controllerInput: ControllerInput) {

    fun isAbleToExecute(input: Int): Boolean {
        return when (input) {
            ConsoleUtil.Commands.trainingListCommand,
            ConsoleUtil.Commands.planTrainingCommand,
            ConsoleUtil.Commands.extendTrainingCommand,
            ConsoleUtil.Commands.createExerciseCommand,
            ConsoleUtil.Commands.completeTrainingCommand, -> true
            else -> false
        }
    }

    fun executeCommand(input: Int) {
        when (input) {
            ConsoleUtil.Commands.trainingListCommand -> requestListOfTrainings()
            ConsoleUtil.Commands.planTrainingCommand -> planAthleticTraining()
            ConsoleUtil.Commands.extendTrainingCommand -> extendTrainingPeriod()
            ConsoleUtil.Commands.completeTrainingCommand -> completeTraining()
            else -> println("Unknown command.")
        }
    }

    private fun requestListOfTrainings() {
        val identifier = ConsoleUtil.requestStringField("Insert the member identifier with which filter trainings")
        controllerInput.executeRequest(ListOfTrainingsControllerRequest(identifier))
    }

    private fun planAthleticTraining() {
        val athleticTrainerId = ConsoleUtil.requestStringField("Insert the athletic trainer identifier")
        val memberId = ConsoleUtil.requestStringField("Insert the member identifier")
        val purpose = ConsoleUtil.requestStringField("Insert the purpose")
        val startDay = ConsoleUtil.requestDateField("Insert the start day of the training")
        val endDay = ConsoleUtil.requestDateField("Insert the end day of the training")
        controllerInput.executeRequest(
            PlanTrainingControllerRequest(
                athleticTrainerId,
                memberId,
                purpose,
                startDay,
                endDay
            )
        )
    }

    private fun extendTrainingPeriod() {
        val trainingId = ConsoleUtil.requestStringField("Insert the training identifier")
        val endDay = ConsoleUtil.requestDateField("Insert the new end day of the training")
        controllerInput.executeRequest(
            ExtendTrainingPeriodControllerRequest(
                trainingId,
                endDay
            )
        )
    }

    private fun completeTraining() {
        val trainingId = ConsoleUtil.requestStringField("Insert the training identifier")
        controllerInput.executeRequest(
            CompleteTrainingControllerRequest(trainingId)
        )
    }
}
