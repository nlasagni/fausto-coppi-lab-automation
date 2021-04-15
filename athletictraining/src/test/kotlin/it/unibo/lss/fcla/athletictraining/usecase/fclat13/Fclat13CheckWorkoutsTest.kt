/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat13

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryWorkoutRepository
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseError
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseResponse

/**
 * Test of the [Fclat13CheckWorkouts] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat13CheckWorkoutsTest : FreeSpec({

    val workoutRepository = InMemoryWorkoutRepository()

    val workoutId = WorkoutId("1234")
    val workoutName = "Workout"
    val workout = Workout(workoutId, workoutName)

    "The use case FCLAT-13 should" - {
        "allow to check all the workouts" - {
            workoutRepository.add(workout.snapshot())
            val useCase = Fclat13CheckWorkouts(
                object : CheckWorkoutsOutput {
                    override fun handleResponse(response: UseCaseResponse<Collection<Workout>>) {
                        response.response.shouldHaveSize(1)
                    }

                    override fun handleError(error: UseCaseError) {
                        // Empty
                    }
                },
                workoutRepository
            )
            useCase.execute(CheckWorkoutsRequest())
        }
    }
})
