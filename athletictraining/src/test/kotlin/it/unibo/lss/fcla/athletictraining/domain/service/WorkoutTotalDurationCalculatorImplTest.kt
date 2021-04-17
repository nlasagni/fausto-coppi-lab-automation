/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.service

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId

/**
 * Test of the [WorkoutTotalDurationCalculator] service implementation.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class WorkoutTotalDurationCalculatorImplTest : FreeSpec({

    lateinit var exerciseId: ExerciseId
    lateinit var name: String
    lateinit var durationOfExecution: Duration
    lateinit var durationOfRest: Duration
    lateinit var intensity: Intensity
    lateinit var distance: Distance
    lateinit var configuration: Configuration
    lateinit var validExercise: Exercise

    beforeAny {
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
        validExercise = Exercise(
            exerciseId,
            name,
            configuration,
            durationOfExecution,
            durationOfRest
        )
    }

    "A WorkoutTotalDurationCalculator should be able to" - {
        "compute the total duration of a workout" - {
            val exercises = listOf(
                validExercise,
                validExercise,
                validExercise
            )
            val totalDuration = exercises.sumOf {
                val snapshot = it.snapshot()
                snapshot.durationOfExecution.seconds + snapshot.durationOfRest.seconds
            }
            val service = WorkoutTotalDurationCalculatorImpl()
            service.computeTotalDuration(exercises).shouldBe(Duration(totalDuration))
        }
    }
})
