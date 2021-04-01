package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.ScheduledWorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * A ScheduledWorkout is a workout that has been scheduled during an [AthleticTraining].
 *
 * A ScheduledWorkout must refer to a [Workout], otherwise a [ScheduledWorkoutIdMissing] is thrown.
 * Furthermore it must have a valid [schedule].
 *
 * The lifecycle of a ScheduledWorkout ends when the related [AthleticTraining] is completed
 * or ends.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class ScheduledWorkout(
    private val workoutId: WorkoutId,
    private val schedule: Schedule
) {
    private val id: ScheduledWorkoutId

    init {
        if (workoutId.value.isEmpty()) {
            throw ScheduledWorkoutIdMissing()
        }
        id = generateId()
    }

    private fun generateId(): ScheduledWorkoutId {
        return ScheduledWorkoutId(
            "${schedule.startTime}-${schedule.endTime}"
        )
    }

    fun snapshot() = ScheduledWorkoutSnapshot(
        workoutId,
        schedule
    )
}
