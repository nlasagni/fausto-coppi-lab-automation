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
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import org.junit.jupiter.api.Assertions

/**
 * Test of the [InMemoryExerciseRepository] class.
 *
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryExerciseRepositoryTest : FreeSpec({

    lateinit var repository: ExerciseRepository
    lateinit var exerciseId: ExerciseId
    lateinit var name: String
    lateinit var durationOfExecution: Duration
    lateinit var durationOfRest: Duration
    lateinit var intensity: Intensity
    lateinit var distance: Distance
    lateinit var configuration: Configuration
    lateinit var exercise: Exercise

    beforeAny {
        repository = InMemoryExerciseRepository()
        exerciseId = ExerciseId(UuidGenerator().generate())
        name = "Exercise 1"
        durationOfExecution = Duration.ofHours(1)
        durationOfRest = Duration.ofMinutes(30)
        intensity = Intensity(Intensity.NORMAL)
        distance = Distance(1000)
        configuration = Configuration(
            GymMachineId("1234"),
            intensity,
            distance
        )
        exercise = Exercise(
            exerciseId,
            name,
            configuration,
            durationOfExecution,
            durationOfRest
        )
    }

    "An InMemoryExerciseRepository should" - {
        "be able to persist exercises" - {
            val persistedExercise = repository.add(exercise.snapshot())
            val snapshot = exercise.snapshot()
            Assertions.assertEquals(snapshot, persistedExercise)
        }
        "be able to update an already present exercise" - {
            val persistedExercise = repository.add(exercise.snapshot())
            val rehydratedExercise = Exercise.rehydrate(persistedExercise)
            val newName = "$name Renamed"
            rehydratedExercise.rename(newName)
            val updatedExercise = repository.update(rehydratedExercise.snapshot())
            updatedExercise.name.shouldBe(newName)
        }
        "be able to remove exercises from memory" - {
            val persistedExercise = repository.add(exercise.snapshot())
            repository.remove(persistedExercise)
            repository.findById(exerciseId).shouldBeNull()
        }
        "be able to find an exercise by id" - {
            val persistedExercise = repository.add(exercise.snapshot())
            val persistedId = persistedExercise.id
            repository.findById(persistedId).shouldNotBeNull()
        }
        "be able to find all exercises" - {
            repository.add(exercise.snapshot())
            repository.findAll().shouldHaveSize(1)
        }
    }
})
