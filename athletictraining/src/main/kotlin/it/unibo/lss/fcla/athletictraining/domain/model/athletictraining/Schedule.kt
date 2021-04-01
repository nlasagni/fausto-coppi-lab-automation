package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfScheduleCannotBeAfterEnd
import java.time.LocalDateTime

/**
 * A ValueObject representing the schedule of a single
 * [it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout].
 *
 * @author Nicola Lasagni on 01/04/2021.
 */
data class Schedule(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
) {

    init {
        if (endTime.isBefore(startTime)) {
            throw BeginningOfScheduleCannotBeAfterEnd()
        }
    }

    fun overlaps(schedule: Schedule): Boolean {
        return startTime.isBefore(schedule.endTime) &&
            endTime.isAfter(schedule.startTime)
    }
}
