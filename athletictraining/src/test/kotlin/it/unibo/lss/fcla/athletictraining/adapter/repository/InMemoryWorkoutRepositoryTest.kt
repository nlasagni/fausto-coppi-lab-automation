/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository
import org.junit.jupiter.api.Assertions

/**
 * Test of the [InMemoryWorkoutRepository] class.
 *
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryWorkoutRepositoryTest : FreeSpec({

    lateinit var repository: WorkoutRepository
    lateinit var workoutId: WorkoutId
    lateinit var workoutName: String
    lateinit var workout: Workout

    beforeAny {
        repository = InMemoryWorkoutRepository()
        workoutId = WorkoutId(UuidGenerator().generate())
        workoutName = "Workout"
        workout = Workout(workoutId, workoutName)
    }

    "An InMemoryWorkoutRepositoryTest should" - {
        "be able to persist workouts" - {
            val persistedWorkout = repository.add(workout.snapshot())
            val snapshot = workout.snapshot()
            Assertions.assertEquals(snapshot, persistedWorkout)
        }
        "be able to update an already present workout" - {
            val persistedWorkout = repository.add(workout.snapshot())
            val rehydratedWorkout = Workout.rehydrate(persistedWorkout)
            rehydratedWorkout.prepareExercise(ExerciseId("1234"))
            val rehydratedSnapshot = rehydratedWorkout.snapshot()
            val updatedWorkout = repository.update(rehydratedSnapshot)
            updatedWorkout.exercises.shouldBe(rehydratedSnapshot.exercises)
        }
        "be able to find a workout by id" - {
            val persistedWorkout = repository.add(workout.snapshot())
            val foundWorkout = repository.findById(workoutId)
            persistedWorkout.shouldBe(foundWorkout)
        }
        "be able to find all workouts" - {
            repository.add(workout.snapshot())
            repository.findAll().shouldHaveSize(1)
        }
    }
})
