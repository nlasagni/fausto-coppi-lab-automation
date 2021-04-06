package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId

/**
 * A snapshot class used to expose all the information about a [Workout].
 *
 * @property name The name of the [Workout] to which this snapshot refers.
 * @property exercises The ordered [List] of exercises of the workout to which this snapshot refers.
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
class WorkoutSnapshot(
    val name: String,
    val exercises: List<ExerciseId>
)
