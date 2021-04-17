/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.config

import it.unibo.lss.fcla.athletictraining.adapter.controller.AthleticTrainingController
import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerManager
import it.unibo.lss.fcla.athletictraining.adapter.controller.ExerciseController
import it.unibo.lss.fcla.athletictraining.adapter.controller.WorkoutController
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.IncrementalGenerator
import it.unibo.lss.fcla.athletictraining.adapter.presenter.BuildWorkoutPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.CancelScheduledWorkoutPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.CheckActiveAthleticTrainingsPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.CheckExercisesPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.CheckWorkoutsPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.ChooseExerciseForWorkoutPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.CompleteAthleticTrainingPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.CreateExercisePresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.DeleteExercisePresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.ExtendAthleticTrainingPeriodPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.PlanAthleticTrainingPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.RemoveExerciseFromWorkoutPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.RescheduleWorkoutPresenter
import it.unibo.lss.fcla.athletictraining.adapter.presenter.ScheduleWorkoutPresenter
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryCompletedAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryWorkoutRepository
import it.unibo.lss.fcla.athletictraining.domain.service.OverlappingAthleticTrainingsCheckerImpl
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculatorImpl
import it.unibo.lss.fcla.athletictraining.infrastructure.service.GymOpenCheckerImpl
import it.unibo.lss.fcla.athletictraining.infrastructure.service.MemberProfileUpdaterImpl
import it.unibo.lss.fcla.athletictraining.infrastructure.ui.ConsoleView
import it.unibo.lss.fcla.athletictraining.usecase.fclat1.Fclat1PlanAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.fclat10.Fclat10CreateExercise
import it.unibo.lss.fcla.athletictraining.usecase.fclat11.Fclat11DeleteExercise
import it.unibo.lss.fcla.athletictraining.usecase.fclat12.Fclat12CheckActiveAthleticTrainings
import it.unibo.lss.fcla.athletictraining.usecase.fclat13.Fclat13CheckWorkouts
import it.unibo.lss.fcla.athletictraining.usecase.fclat14.Fclat14CheckExercises
import it.unibo.lss.fcla.athletictraining.usecase.fclat2.Fclat2ExtendAthleticTrainingPeriod
import it.unibo.lss.fcla.athletictraining.usecase.fclat3.Fclat3CompleteAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.fclat4.Fclat4ScheduleWorkout
import it.unibo.lss.fcla.athletictraining.usecase.fclat5.Fclat5RescheduleWorkout
import it.unibo.lss.fcla.athletictraining.usecase.fclat6.Fclat6CancelScheduledWorkout
import it.unibo.lss.fcla.athletictraining.usecase.fclat7.Fclat7BuildWorkout
import it.unibo.lss.fcla.athletictraining.usecase.fclat8.Fclat8ChooseExerciseForWorkout
import it.unibo.lss.fcla.athletictraining.usecase.fclat9.Fclat9RemoveExerciseFromWorkout

/**
 * Class that represents the whole application.
 *
 * It wraps up all the configuration steps of this microservice.
 *
 * It is responsible of creating components of all layers and to assemble them.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class AthleticTrainingApplication {

    // Repositories and Services
    private val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()
    private val completedAthleticTrainingRepository = InMemoryCompletedAthleticTrainingRepository()
    private val workoutRepository = InMemoryWorkoutRepository()
    private val exerciseRepository = InMemoryExerciseRepository()
    private val idGenerator = IncrementalGenerator()
    private val gymOpenChecker = GymOpenCheckerImpl()
    private val memberProfileUpdater = MemberProfileUpdaterImpl()
    private val overlappingAthleticTrainingsChecker = OverlappingAthleticTrainingsCheckerImpl()
    private val workoutTotalDurationCalculator = WorkoutTotalDurationCalculatorImpl()

    // View
    private val view = ConsoleView()

    // Presenters
    private val planAthleticTrainingPresenter = PlanAthleticTrainingPresenter(view)
    private val extendAthleticTrainingPeriodPresenter = ExtendAthleticTrainingPeriodPresenter(view)
    private val completeAthleticTrainingPresenter = CompleteAthleticTrainingPresenter(view)
    private val scheduleWorkoutPresenter = ScheduleWorkoutPresenter(view)
    private val rescheduleWorkoutPresenter = RescheduleWorkoutPresenter(view)
    private val cancelScheduledWorkoutPresenter = CancelScheduledWorkoutPresenter(view)
    private val buildWorkoutPresenter = BuildWorkoutPresenter(view)
    private val chooseExerciseForWorkoutPresenter = ChooseExerciseForWorkoutPresenter(view)
    private val removeExerciseFromWorkoutPresenter = RemoveExerciseFromWorkoutPresenter(view)
    private val createExercisePresenter = CreateExercisePresenter(view)
    private val deleteExercisePresenter = DeleteExercisePresenter(view)
    private val checkActiveAthleticTrainingsPresenter = CheckActiveAthleticTrainingsPresenter(view)
    private val checkWorkoutsPresenter = CheckWorkoutsPresenter(view)
    private val checkExercisesPresenter = CheckExercisesPresenter(view)

    // Use Cases
    private val fclat1PlanAthleticTraining = Fclat1PlanAthleticTraining(
        planAthleticTrainingPresenter,
        idGenerator,
        memberProfileUpdater,
        activeAthleticTrainingRepository,
        overlappingAthleticTrainingsChecker
    )
    private val fclat2ExtendAthleticTrainingPeriod = Fclat2ExtendAthleticTrainingPeriod(
        extendAthleticTrainingPeriodPresenter,
        memberProfileUpdater,
        activeAthleticTrainingRepository,
        overlappingAthleticTrainingsChecker
    )
    private val fclat3CompleteAthleticTraining = Fclat3CompleteAthleticTraining(
        completeAthleticTrainingPresenter,
        idGenerator,
        memberProfileUpdater,
        activeAthleticTrainingRepository,
        completedAthleticTrainingRepository
    )
    private val fclat4ScheduleWorkout = Fclat4ScheduleWorkout(
        scheduleWorkoutPresenter,
        gymOpenChecker,
        memberProfileUpdater,
        workoutTotalDurationCalculator,
        activeAthleticTrainingRepository,
        workoutRepository,
        exerciseRepository
    )
    private val fclat5RescheduleWorkout = Fclat5RescheduleWorkout(
        rescheduleWorkoutPresenter,
        gymOpenChecker,
        memberProfileUpdater,
        workoutTotalDurationCalculator,
        activeAthleticTrainingRepository,
        workoutRepository,
        exerciseRepository
    )
    private val fclat6CancelScheduledWorkout = Fclat6CancelScheduledWorkout(
        cancelScheduledWorkoutPresenter,
        memberProfileUpdater,
        workoutTotalDurationCalculator,
        activeAthleticTrainingRepository,
        workoutRepository,
        exerciseRepository
    )
    private val fclat7BuildWorkout = Fclat7BuildWorkout(
        buildWorkoutPresenter,
        idGenerator,
        workoutRepository
    )
    private val fclat8ChooseExerciseForWorkout = Fclat8ChooseExerciseForWorkout(
        chooseExerciseForWorkoutPresenter,
        workoutRepository,
        exerciseRepository
    )
    private val fclat9RemoveExerciseFromWorkout = Fclat9RemoveExerciseFromWorkout(
        removeExerciseFromWorkoutPresenter,
        workoutRepository
    )
    private val fclat10CreateExercise = Fclat10CreateExercise(
        createExercisePresenter,
        idGenerator,
        exerciseRepository
    )
    private val fclat11DeleteExercise = Fclat11DeleteExercise(
        deleteExercisePresenter,
        exerciseRepository
    )
    private val fclat12CheckActiveAthleticTrainings = Fclat12CheckActiveAthleticTrainings(
        checkActiveAthleticTrainingsPresenter,
        activeAthleticTrainingRepository
    )
    private val fclat13CheckWorkouts = Fclat13CheckWorkouts(
        checkWorkoutsPresenter,
        workoutRepository
    )
    private val fclat14CheckExercises = Fclat14CheckExercises(
        checkExercisesPresenter,
        exerciseRepository
    )

    // Controllers
    private val athleticTrainingController = AthleticTrainingController(
        fclat1PlanAthleticTraining,
        fclat2ExtendAthleticTrainingPeriod,
        fclat3CompleteAthleticTraining,
        fclat12CheckActiveAthleticTrainings
    )
    private val workoutController = WorkoutController(
        fclat4ScheduleWorkout,
        fclat5RescheduleWorkout,
        fclat6CancelScheduledWorkout,
        fclat7BuildWorkout,
        fclat13CheckWorkouts
    )
    private val exerciseController = ExerciseController(
        fclat8ChooseExerciseForWorkout,
        fclat9RemoveExerciseFromWorkout,
        fclat10CreateExercise,
        fclat11DeleteExercise,
        fclat14CheckExercises
    )
    private val controllerManager = ControllerManager(
        athleticTrainingController,
        workoutController,
        exerciseController
    )

    /**
     * Starts the application.
     */
    fun start() {
        view.registerController(controllerManager)
        view.start()
    }
}
