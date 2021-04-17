/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryWorkoutRepository
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.DummyRemoveExerciseFromWorkoutPresenter

/**
 * Test of the [Fclat9RemoveExerciseFromWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat9RemoveExerciseFromWorkoutTest : FreeSpec({

    val workoutRepository = InMemoryWorkoutRepository()
    val exerciseRepository = InMemoryExerciseRepository()
    val useCase = Fclat9RemoveExerciseFromWorkout(
        DummyRemoveExerciseFromWorkoutPresenter(),
        workoutRepository
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

    "The use case FCLAT-9 should" - {
        "allow remove an exercise from a workout" - {
            workout.prepareExercise(exerciseId)
            exerciseRepository.add(exercise.snapshot())
            workoutRepository.add(workout.snapshot())
            val request = RemoveExerciseRequest(
                workoutId.value,
                0
            )
            useCase.execute(request)
            val savedWorkout = workoutRepository.findById(workoutId)
            savedWorkout?.exercises?.shouldBeEmpty()
        }
    }
})
