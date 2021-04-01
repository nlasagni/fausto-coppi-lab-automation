package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfScheduleCannotBeAfterEnd
import java.time.LocalDate
import java.time.LocalTime

/**
 * A ValueObject representing the schedule of a single
 * [it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout].
 *
 * @author Nicola Lasagni on 01/04/2021.
 */
data class Schedule(
    val day: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
) {

    init {
        if (endTime.isBefore(startTime)) {
            throw BeginningOfScheduleCannotBeAfterEnd()
        }
    }

    fun overlaps(schedule: Schedule): Boolean {
        return day.isEqual(schedule.day) &&
            startTime.isBefore(schedule.endTime) &&
            endTime.isAfter(schedule.startTime)
    }
}
