package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId

/**
 * A Workout that is scheduled during an
 * [it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining].
 *
 * @author Nicola Lasagni on 25/02/2021.
 */
class Workout(
    private val name: String
) {

    private val id: WorkoutId
    private var exercises: List<ExerciseId> = emptyList()

    init {
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of this Workout which will be stored
     * into the [id] private property.
     */
    private fun generateId() = WorkoutId("$name")

    /**
     * Generates an [WorkoutSnapshot] with the information about this Workout.
     */
    fun snapshot(): WorkoutSnapshot = WorkoutSnapshot(name, exercises)

    /**
     * Prepares an [Exercise] for this Workout.
     */
    fun prepareExercise(exerciseId: ExerciseId) {
        exercises = exercises + exerciseId
    }
}
