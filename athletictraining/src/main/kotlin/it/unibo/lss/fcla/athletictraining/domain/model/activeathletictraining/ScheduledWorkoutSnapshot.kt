package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * A snapshot class used to expose all the relevant information about a [ScheduledWorkout].
 *
 * @property id The [ScheduledWorkoutId] to which this snapshot refers.
 * @property workout The [WorkoutId] of the [ScheduledWorkout] to which this snapshot refers.
 * @property schedule The [Schedule] of the [ScheduledWorkout] to which this snapshot refers.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
data class ScheduledWorkoutSnapshot(
    val id: ScheduledWorkoutId,
    val workout: WorkoutId,
    val schedule: Schedule
)
