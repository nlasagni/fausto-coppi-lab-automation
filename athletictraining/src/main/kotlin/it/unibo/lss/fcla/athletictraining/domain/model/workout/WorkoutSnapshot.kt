package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId

/**
 * A snapshot class used to expose all the information about a [Workout].
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
class WorkoutSnapshot(
    val name: String,
    val exercises: List<ExerciseId>
)
