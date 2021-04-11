package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.InvalidExerciseIndex
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.NameMustNotBeEmpty

/**
 * A Workout that can be scheduled during an ActiveAthleticTraining.
 *
 * It is possible to prepare or cancel one or more exercises for a Workout.
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
     * Cancels the exercise of this Workout at the [index] specified.
     * If the [index] provided is invalid, an [InvalidExerciseIndex] is thrown.
     */
    fun cancelExercise(index: Int) {
        val exercise = exercises.getOrNull(index) ?: throw InvalidExerciseIndex()
        exercises = exercises - exercise
    }

    override fun toString(): String {
        return "Workout(id=$id, name='$name', exercises=$exercises)"
    }
}
