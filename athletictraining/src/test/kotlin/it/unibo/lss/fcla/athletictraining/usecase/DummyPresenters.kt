package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
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
 * @author Nicola Lasagni on 11/04/2021.
 */

/**
 * An abstract dummy presenter created for test purposes.
 */
abstract class AbstractDummyPresenter<T> : UseCaseOutput<T> {

    override fun handleResponse(response: UseCaseResponse<T>) {
        // Dummy empty implementation
    }

    override fun handleError(error: UseCaseError) {
        // Dummy empty implementation
    }
}

class DummyPlanAthleticTrainingPresenter() :
    PlanAthleticTrainingOutput,
    AbstractDummyPresenter<ActiveAthleticTraining>()

class DummyExtendAthleticTrainingPeriodPresenter() :
    ExtendAthleticTrainingPeriodOutput,
    AbstractDummyPresenter<ActiveAthleticTraining>()

class DummyCompleteAthleticTrainingPresenter() :
    CompleteAthleticTrainingOutput,
    AbstractDummyPresenter<CompletedAthleticTraining>()

class DummyScheduleWorkoutPresenter() :
    ScheduleWorkoutOutput,
    AbstractDummyPresenter<ActiveAthleticTraining>()

class DummyRescheduleWorkoutPresenter() :
    RescheduleWorkoutOutput,
    AbstractDummyPresenter<ActiveAthleticTraining>()

class DummyCancelScheduledWorkoutPresenter() :
    CancelScheduledWorkoutOutput,
    AbstractDummyPresenter<Boolean>()

class DummyBuildWorkoutPresenter() :
    BuildWorkoutOutput,
    AbstractDummyPresenter<Workout>()

class DummyChooseExerciseForWorkoutPresenter() :
    ChooseExerciseForWorkoutOutput,
    AbstractDummyPresenter<Workout>()

class DummyRemoveExerciseFromWorkoutPresenter() :
    RemoveExerciseFromWorkoutOutput,
    AbstractDummyPresenter<Workout>()

class DummyCreateExercisePresenter() :
    CreateExerciseOutput,
    AbstractDummyPresenter<Exercise>()

class DummyDeleteExercisePresenter() :
    DeleteExerciseOutput,
    AbstractDummyPresenter<Boolean>()

class DummyCheckActiveAthleticTrainingsPresenter() :
    CheckActiveAthleticTrainingsOutput,
    AbstractDummyPresenter<Collection<ActiveAthleticTraining>>()

class DummyCheckWorkoutsPresenter() :
    CheckWorkoutsOutput,
    AbstractDummyPresenter<Collection<Workout>>()

class DummyCheckExercisesPresenter() :
    CheckExercisesOutput,
    AbstractDummyPresenter<Collection<Exercise>>()
