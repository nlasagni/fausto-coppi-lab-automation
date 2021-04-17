/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat7

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.IncrementalGenerator
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryWorkoutRepository
import it.unibo.lss.fcla.athletictraining.usecase.DummyBuildWorkoutPresenter

/**
 * Test of the [Fclat7BuildWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat7BuildWorkoutTest : FreeSpec({

    val idGenerator = IncrementalGenerator()
    val workoutRepository = InMemoryWorkoutRepository()
    val useCase = Fclat7BuildWorkout(
        DummyBuildWorkoutPresenter(),
        idGenerator,
        workoutRepository
    )
    val workoutName = "Workout"

    "The use case FCLAT-7 should" - {
        "allow to build a workout" - {
            val request = BuildWorkoutRequest(
                workoutName
            )
            useCase.execute(request)
            val savedWorkouts = workoutRepository.findAll()
            savedWorkouts.shouldHaveSize(1)
            savedWorkouts.first().name.shouldBe(workoutName)
        }
    }
})
