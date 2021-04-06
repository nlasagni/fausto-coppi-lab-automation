package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * A snapshot class used to expose all the information about a [ScheduledWorkout].
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
data class ScheduledWorkoutSnapshot(
    val id: ScheduledWorkoutId,
    val workoutId: WorkoutId,
    val schedule: Schedule
)
