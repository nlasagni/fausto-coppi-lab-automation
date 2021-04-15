package it.unibo.lss.fcla.athletictraining.usecase.fclat11

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.usecase.DummyDeleteExercisePresenter

/**
 * Test of the [Fclat11DeleteExercise] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat11DeleteExerciseTest : FreeSpec({

    val exerciseRepository = InMemoryExerciseRepository()
    val useCase = Fclat11DeleteExercise(
        DummyDeleteExercisePresenter(),
        exerciseRepository
    )

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

    "The use case FCLAT-11 should" - {
        "allow the deletion of an exercise" - {
            exerciseRepository.add(exercise.snapshot())
            val request = DeleteExerciseRequest(
                exerciseId.value
            )
            useCase.execute(request)
            exerciseRepository.findAll().shouldBeEmpty()
        }
    }
})
