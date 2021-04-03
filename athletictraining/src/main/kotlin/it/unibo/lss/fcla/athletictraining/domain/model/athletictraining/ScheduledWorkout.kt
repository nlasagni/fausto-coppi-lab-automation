package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * A ScheduledWorkout is a workout that has been scheduled during an [AthleticTraining].
 *
 * A ScheduledWorkout must refer to a [Workout], otherwise a [WorkoutIdMissing] is thrown.
 * Furthermore it must have a valid [Schedule].
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class ScheduledWorkout(
    private val workoutId: WorkoutId,
    private var schedule: Schedule
) {
    val id: ScheduledWorkoutId

    init {
        if (workoutId.value.isEmpty()) {
            throw WorkoutIdMissing()
        }
        id = generateId()
    }

    private fun generateId(): ScheduledWorkoutId {
        return ScheduledWorkoutId(
            "$workoutId-${schedule.day}-${schedule.startTime}-${schedule.endTime}"
        )
    }

    /**
     * Retrieves the [WorkoutId] of this [ScheduledWorkout].
     * @return The [WorkoutId] of this [ScheduledWorkout].
     */
    fun scheduledForWorkout(): WorkoutId {
        return workoutId
    }

    /**
     * Retrieves the [Schedule] of this [ScheduledWorkout].
     * @return The [Schedule] of this [ScheduledWorkout].
     */
    fun scheduledOn(): Schedule {
        return schedule
    }

    /**
     * Reschedules this ScheduledWorkout with the specified [schedule].
     */
    fun reschedule(newSchedule: Schedule) {
        schedule = newSchedule
    }

    /**
     * Generates a snapshot with the information about this ScheduledWorkout.
     * @return A [ScheduledWorkoutSnapshot] with the information about this ScheduledWorkout.
     */
    fun snapshot() = ScheduledWorkoutSnapshot(
        id,
        workoutId,
        schedule
    )
}
