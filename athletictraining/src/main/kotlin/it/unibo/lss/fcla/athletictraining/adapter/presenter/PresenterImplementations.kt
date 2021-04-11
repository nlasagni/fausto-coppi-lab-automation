package it.unibo.lss.fcla.athletictraining.adapter.presenter

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.usecase.fclat1.PlanAthleticTrainingOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat10.CreateExerciseOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat11.DeleteExerciseOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat12.CheckActiveAthleticTrainingsOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat13.CheckWorkoutsOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat14.CheckExercisesOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat2.ExtendAthleticTrainingPeriodOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat3.CompleteAthleticTrainingOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat4.ScheduleWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat5.RescheduleWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat6.CancelScheduledWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat7.BuildWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat8.ChooseExerciseForWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.fclat9.RemoveExerciseFromWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseError
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseResponse
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * @author Nicola Lasagni on 10/04/2021.
 */

/**
 * A generic [UseCaseOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
abstract class AbstractPresenter<T>(
    private val presenterOutput: PresenterOutput
) : UseCaseOutput<T> {
    override fun handleResponse(response: UseCaseResponse<T>) {
        presenterOutput.renderViewModel(
            ViewModel(response.response.toString().replace("), ", "),\n "))
        )
    }

    override fun handleError(error: UseCaseError) {
        presenterOutput.renderViewModel(ViewModel(error.toString()))
    }
}

/**
 * A [PlanAthleticTrainingOutput]  which processes output data coming from use cases for presentation
 * purposes.
 */
class PlanAthleticTrainingPresenter(presenterOutput: PresenterOutput) :
    PlanAthleticTrainingOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

/**
 * An [ExtendAthleticTrainingPeriodOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class ExtendAthleticTrainingPeriodPresenter(presenterOutput: PresenterOutput) :
    ExtendAthleticTrainingPeriodOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

/**
 * A [CompleteAthleticTrainingOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class CompleteAthleticTrainingPresenter(presenterOutput: PresenterOutput) :
    CompleteAthleticTrainingOutput,
    AbstractPresenter<CompletedAthleticTraining>(presenterOutput)

/**
 * A [CancelScheduledWorkoutOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class ScheduleWorkoutPresenter(presenterOutput: PresenterOutput) :
    ScheduleWorkoutOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

/**
 * A [RescheduleWorkoutOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class RescheduleWorkoutPresenter(presenterOutput: PresenterOutput) :
    RescheduleWorkoutOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

/**
 * A [CancelScheduledWorkoutOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class CancelScheduledWorkoutPresenter(presenterOutput: PresenterOutput) :
    CancelScheduledWorkoutOutput,
    AbstractPresenter<Boolean>(presenterOutput)

/**
 * A [BuildWorkoutOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class BuildWorkoutPresenter(presenterOutput: PresenterOutput) :
    BuildWorkoutOutput,
    AbstractPresenter<Workout>(presenterOutput)

/**
 * A [CheckExercisesOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class ChooseExerciseForWorkoutPresenter(presenterOutput: PresenterOutput) :
    ChooseExerciseForWorkoutOutput,
    AbstractPresenter<Workout>(presenterOutput)

/**
 * A [RemoveExerciseFromWorkoutOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class RemoveExerciseFromWorkoutPresenter(presenterOutput: PresenterOutput) :
    RemoveExerciseFromWorkoutOutput,
    AbstractPresenter<Workout>(presenterOutput)

/**
 * A [CreateExerciseOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class CreateExercisePresenter(presenterOutput: PresenterOutput) :
    CreateExerciseOutput,
    AbstractPresenter<Exercise>(presenterOutput)

/**
 * A [DeleteExerciseOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class DeleteExercisePresenter(presenterOutput: PresenterOutput) :
    DeleteExerciseOutput,
    AbstractPresenter<Boolean>(presenterOutput)

/**
 * A [CheckActiveAthleticTrainingsOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class CheckActiveAthleticTrainingsPresenter(presenterOutput: PresenterOutput) :
    CheckActiveAthleticTrainingsOutput,
    AbstractPresenter<Collection<ActiveAthleticTraining>>(presenterOutput)

/**
 * A [CheckWorkoutsOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class CheckWorkoutsPresenter(presenterOutput: PresenterOutput) :
    CheckWorkoutsOutput,
    AbstractPresenter<Collection<Workout>>(presenterOutput)

/**
 * A [CheckExercisesOutput] which processes output data coming from use cases for presentation
 * purposes.
 */
class CheckExercisesPresenter(presenterOutput: PresenterOutput) :
    CheckExercisesOutput,
    AbstractPresenter<Collection<Exercise>>(presenterOutput)
