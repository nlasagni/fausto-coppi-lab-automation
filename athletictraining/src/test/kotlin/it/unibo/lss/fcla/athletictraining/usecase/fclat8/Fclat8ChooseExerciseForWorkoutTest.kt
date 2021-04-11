package it.unibo.lss.fcla.athletictraining.usecase.fclat8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
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
import it.unibo.lss.fcla.athletictraining.usecase.DummyChooseExerciseForWorkoutPresenter

/**
 * Test of the [Fclat8ChooseExerciseForWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat8ChooseExerciseForWorkoutTest : FreeSpec({

    val workoutRepository = InMemoryWorkoutRepository()
    val exerciseRepository = InMemoryExerciseRepository()
    val useCase = Fclat8ChooseExerciseForWorkout(
        DummyChooseExerciseForWorkoutPresenter(),
        workoutRepository,
        exerciseRepository
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

    "The use case FCLAT-8 should" - {
        "allow to choose an exercise for a workout" - {
            exerciseRepository.add(exercise.snapshot())
            workoutRepository.add(workout.snapshot())
            val request = ChooseExerciseRequest(
                workoutId.value,
                exerciseId.value
            )
            useCase.execute(request)
            val savedWorkout = workoutRepository.findById(workoutId)
            savedWorkout?.exercises?.shouldContain(exerciseId)
        }
    }
})
