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

class PlanAthleticTrainingPresenter(presenterOutput: PresenterOutput) :
    PlanAthleticTrainingOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

class ExtendAthleticTrainingPeriodPresenter(presenterOutput: PresenterOutput) :
    ExtendAthleticTrainingPeriodOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

class CompleteAthleticTrainingPresenter(presenterOutput: PresenterOutput) :
    CompleteAthleticTrainingOutput,
    AbstractPresenter<CompletedAthleticTraining>(presenterOutput)

class ScheduleWorkoutPresenter(presenterOutput: PresenterOutput) :
    ScheduleWorkoutOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

class RescheduleWorkoutPresenter(presenterOutput: PresenterOutput) :
    RescheduleWorkoutOutput,
    AbstractPresenter<ActiveAthleticTraining>(presenterOutput)

class CancelScheduledWorkoutPresenter(presenterOutput: PresenterOutput) :
    CancelScheduledWorkoutOutput,
    AbstractPresenter<Boolean>(presenterOutput)

class BuildWorkoutPresenter(presenterOutput: PresenterOutput) :
    BuildWorkoutOutput,
    AbstractPresenter<Workout>(presenterOutput)

class ChooseExerciseForWorkoutPresenter(presenterOutput: PresenterOutput) :
    ChooseExerciseForWorkoutOutput,
    AbstractPresenter<Workout>(presenterOutput)

class RemoveExerciseFromWorkoutPresenter(presenterOutput: PresenterOutput) :
    RemoveExerciseFromWorkoutOutput,
    AbstractPresenter<Workout>(presenterOutput)

class CreateExercisePresenter(presenterOutput: PresenterOutput) :
    CreateExerciseOutput,
    AbstractPresenter<Exercise>(presenterOutput)

class DeleteExercisePresenter(presenterOutput: PresenterOutput) :
    DeleteExerciseOutput,
    AbstractPresenter<Boolean>(presenterOutput)

class CheckActiveAthleticTrainingsPresenter(presenterOutput: PresenterOutput) :
    CheckActiveAthleticTrainingsOutput,
    AbstractPresenter<Collection<ActiveAthleticTraining>>(presenterOutput)

class CheckWorkoutsPresenter(presenterOutput: PresenterOutput) :
    CheckWorkoutsOutput,
    AbstractPresenter<Collection<Workout>>(presenterOutput)

class CheckExercisesPresenter(presenterOutput: PresenterOutput) :
    CheckExercisesOutput,
    AbstractPresenter<Collection<Exercise>>(presenterOutput)
