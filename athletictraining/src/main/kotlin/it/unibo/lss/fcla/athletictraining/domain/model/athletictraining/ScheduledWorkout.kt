package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * A ScheduledWorkout is a workout that has been scheduled during an [AthleticTraining].
 *
 * A ScheduledWorkout must refer to a [Workout], otherwise a [WorkoutIdMissing] is thrown.
 * Furthermore it must have a valid [schedule].
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class ScheduledWorkout(
    private val workoutId: WorkoutId,
    private var schedule: Schedule
) {
    private val id: ScheduledWorkoutId

    init {
        if (workoutId.value.isEmpty()) {
            throw WorkoutIdMissing()
        }
        id = generateId()
    }

    private fun generateId(): ScheduledWorkoutId {
        return ScheduledWorkoutId(
            "${schedule.startTime}-${schedule.endTime}"
        )
    }

    /**
     * Reschedules this ScheduledWorkout with the specified [schedule].
     */
    fun reschedule(newSchedule: Schedule) {
        schedule = newSchedule
    }

    /**
     * Checks if the specified [scheduledWorkout] overlaps with this ScheduledWorkout.
     */
    fun overlapsWith(scheduledWorkout: ScheduledWorkout): Boolean {
        return schedule.overlapsWith(scheduledWorkout.schedule)
    }

    /**
     * Generates an [ScheduledWorkoutSnapshot] with the information about this ScheduledWorkout.
     */
    fun snapshot() = ScheduledWorkoutSnapshot(
        id,
        workoutId,
        schedule
    )
}
