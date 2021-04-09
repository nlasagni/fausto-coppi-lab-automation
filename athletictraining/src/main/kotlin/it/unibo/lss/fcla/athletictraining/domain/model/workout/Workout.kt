package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.NameMustNotBeEmpty

/**
 * A Workout that is scheduled during an ActiveAthleticTraining.
 *
 * It is possible to prepare or cancel one or more exercises for a Workout, following the LIFO
 * order.
 *
 * @property id The unique id of this [Workout].
 *
 * @author Nicola Lasagni on 25/02/2021.
 */
class Workout(
    val id: WorkoutId,
    private val name: String
) {

    private var exercises: List<ExerciseId> = emptyList()

    init {
        if (id.value.isEmpty()) {
            throw WorkoutIdMissing()
        }
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
    }

    /**
     * Generates a [WorkoutSnapshot] with the information about this Workout.
     */
    fun snapshot(): WorkoutSnapshot = WorkoutSnapshot(
        name,
        exercises
    )

    /**
     * Prepares an [Exercise] for this Workout.
     */
    fun prepareExercise(exerciseId: ExerciseId) {
        exercises = exercises + exerciseId
    }

    /**
     * Cancels the last prepared [Exercise] prepared for this Workout.
     */
    fun cancelLastExercise() {
        if (exercises.isNotEmpty()) {
            exercises = exercises - exercises.last()
        }
    }
}
