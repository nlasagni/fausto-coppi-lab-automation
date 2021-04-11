package it.unibo.lss.fcla.athletictraining.adapter.controller

/**
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
