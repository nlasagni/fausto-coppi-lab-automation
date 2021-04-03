package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.WorkoutIdMissing

/**
 * A Workout that is scheduled during an
 * [it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining].
 *
 * @author Nicola Lasagni on 25/02/2021.
 */
class Workout(
    private val id: WorkoutId,
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
