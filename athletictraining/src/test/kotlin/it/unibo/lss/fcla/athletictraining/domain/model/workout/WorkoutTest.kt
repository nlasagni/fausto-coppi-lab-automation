/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.workout

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.InvalidExerciseIndex
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.NameMustNotBeEmpty
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

/**
 * Test of the [Workout] domain Entity.
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
class WorkoutTest : FreeSpec({
    lateinit var workoutId: WorkoutId
    lateinit var workoutName: String

    /**
     * Setup before every test.
     */
    beforeAny {
        workoutId = WorkoutId(UuidGenerator().generate())
        workoutName = "Workout"
    }

    "Workout should" - {
        "have a unique id" - {
            assertThrows<WorkoutIdMissing> {
                Workout(WorkoutId(""), workoutName)
            }
        }
        "have a name" - {
            assertThrows<NameMustNotBeEmpty> {
                Workout(workoutId, "")
            }
        }
        "offer a snapshot of itself" - {
            val workout = Workout(workoutId, workoutName)
            val snapshot = workout.snapshot()
            Assertions.assertEquals(workoutName, snapshot.name)
        }
        "allow the preparation of exercises" - {
            val workout = Workout(workoutId, workoutName)
            val exerciseId = ExerciseId("1234")
            assertDoesNotThrow {
                workout.prepareExercise(exerciseId)
            }
            val snapshot = workout.snapshot()
            Assertions.assertEquals(snapshot.exercises.size, 1)
        }
        "allow the cancellation of exercises" - {
            val workout = Workout(workoutId, workoutName)
            val exerciseId = ExerciseId("1234")
            workout.prepareExercise(exerciseId)
            assertThrows<InvalidExerciseIndex> {
                workout.cancelExercise(2)
            }
            val snapshot = workout.snapshot()
            assertDoesNotThrow { workout.cancelExercise(snapshot.exercises.indexOf(exerciseId)) }
        }
    }
})
