/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryWorkoutRepository
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculatorImpl
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.infrastructure.service.GymOpenCheckerImpl
import it.unibo.lss.fcla.athletictraining.infrastructure.service.MemberProfileUpdaterImpl
import it.unibo.lss.fcla.athletictraining.usecase.DummyRescheduleWorkoutPresenter
import java.time.LocalDate
import java.time.LocalTime

/**
 * Test of the [Fclat5RescheduleWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat5RescheduleWorkoutTest : FreeSpec({

    val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()
    val workoutRepository = InMemoryWorkoutRepository()
    val exerciseRepository = InMemoryExerciseRepository()
    val gymOpenChecker = GymOpenCheckerImpl()
    val memberProfileUpdater = MemberProfileUpdaterImpl()
    val workoutTotalDurationCalculator = WorkoutTotalDurationCalculatorImpl()
    val useCase = Fclat5RescheduleWorkout(
        DummyRescheduleWorkoutPresenter(),
        gymOpenChecker,
        memberProfileUpdater,
        workoutTotalDurationCalculator,
        activeAthleticTrainingRepository,
        workoutRepository,
        exerciseRepository
    )

    val activeAthleticTrainingId = ActiveAthleticTrainingId("1234")
    val athleticTrainer = AthleticTrainerId("1234")
    val member = MemberId("1234")
    val beginning = LocalDate.now()
    val end = beginning.plusMonths(1)
    val period = Period(beginning, end)
    val purpose = Purpose("Athletic Preparation")
    val activeAthleticTraining = ActiveAthleticTraining(
        activeAthleticTrainingId,
        athleticTrainer,
        member,
        purpose,
        period
    )
    val tenOClock = LocalTime.of(10, 0)
    val schedule = Schedule(
        beginning,
        tenOClock,
        tenOClock.plusHours(1)
    )
    val workoutId = WorkoutId("1234")
    val workoutName = "Workout"
    val workout = Workout(workoutId, workoutName)
    val exerciseId = ExerciseId("1234")
    val name = "Exercise 1"
    val durationOfExecution = Duration.ofHours(1)
    val durationOfRest = Duration.ofMinutes(30)
    val intensity = Intensity(Intensity.NORMAL)
    val distance = Distance(1000)
    val configuration = Configuration(
        GymMachineId("1234"),
        intensity,
        distance
    )
    val exercise = Exercise(
        exerciseId,
        name,
        configuration,
        durationOfExecution,
        durationOfRest
    )

    "The use case FCLAT-5 should" - {
        "allow the rescheduling of a workout" - {
            workout.prepareExercise(exerciseId)
            workout.prepareExercise(exerciseId)
            activeAthleticTraining.scheduleWorkout(workoutId, schedule)
            exerciseRepository.add(exercise.snapshot())
            workoutRepository.add(workout.snapshot())
            activeAthleticTrainingRepository.add(activeAthleticTraining.snapshot())
            val request = RescheduleWorkoutRequest(
                activeAthleticTrainingId.value,
                workoutId.value,
                schedule.day,
                schedule.startTime,
                schedule.day.plusDays(1),
                schedule.startTime
            )
            useCase.execute(request)
            val savedAthleticTraining =
                activeAthleticTrainingRepository.findById(activeAthleticTrainingId)
            savedAthleticTraining?.scheduledWorkouts?.shouldHaveSize(1)
            savedAthleticTraining
                ?.scheduledWorkouts
                ?.first()
                ?.workout
                ?.shouldBe(workoutId)
        }
    }
})
