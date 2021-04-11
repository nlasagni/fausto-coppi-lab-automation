package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.WorkoutReferenceMissing
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * A ScheduledWorkout is a workout that has been scheduled during an athletic training.
 *
 * A ScheduledWorkout must refer to a Workout, otherwise a [WorkoutIdMissing] is thrown.
 * Furthermore it must have a valid [Schedule].
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class ScheduledWorkout(
    private val workout: WorkoutId,
    private var schedule: Schedule
) {
    val id: ScheduledWorkoutId

    init {
        if (workout.value.isEmpty()) {
            throw WorkoutReferenceMissing()
        }
        id = generateId()
    }

    private fun generateId(): ScheduledWorkoutId {
        return ScheduledWorkoutId(workout, schedule)
    }

    /**
     * Retrieves the [WorkoutId] of this [ScheduledWorkout].
     * @return The [WorkoutId] of this [ScheduledWorkout].
     */
    fun scheduledForWorkout(): WorkoutId {
        return workout
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
        workout,
        schedule
    )

    override fun toString(): String {
        return "ScheduledWorkout(workout=$workout, schedule=$schedule, id=$id)"
    }
}
