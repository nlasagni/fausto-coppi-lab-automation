/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat14

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseError
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseResponse

/**
 * Test of the [Fclat14CheckExercises] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat14CheckExercisesTest : FreeSpec({

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
    val validExercise = Exercise(
        exerciseId,
        name,
        configuration,
        durationOfExecution,
        durationOfRest
    )

    val exercisesRepository = InMemoryExerciseRepository()

    "The use case FCLAT-14 should" - {
        "allow to check of created exercises" - {
            val useCase = Fclat14CheckExercises(
                object : CheckExercisesOutput {
                    override fun handleResponse(response: UseCaseResponse<Collection<Exercise>>) {
                        response.response.shouldHaveSize(1)
                    }

                    override fun handleError(error: UseCaseError) {
                        // Empty
                    }
                },
                exercisesRepository
            )
            exercisesRepository.add(validExercise.snapshot())
            val request = CheckExercisesRequest()
            useCase.execute(request)
        }
    }
})
