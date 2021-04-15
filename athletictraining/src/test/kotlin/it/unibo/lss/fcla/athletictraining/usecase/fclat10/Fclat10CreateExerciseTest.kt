/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.IncrementalGenerator
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.usecase.DummyCreateExercisePresenter

/**
 * Test of the [Fclat10CreateExercise] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat10CreateExerciseTest : FreeSpec({

    val idGenerator = IncrementalGenerator()
    val exerciseRepository = InMemoryExerciseRepository()
    val useCase = Fclat10CreateExercise(
        DummyCreateExercisePresenter(),
        idGenerator,
        exerciseRepository
    )

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

    "The use case FCLAT-10 should" - {
        "allow the creation of an exercise" - {
            val request = CreateExerciseRequest(
                configuration.gymMachine.value,
                name,
                durationOfExecution.seconds.toInt(),
                durationOfRest.seconds.toInt(),
                configuration.intensity.amount,
                configuration.distance.meters
            )
            useCase.execute(request)
            val savedExercises =
                exerciseRepository.findAll()
            savedExercises.shouldHaveSize(1)
        }
    }
})
