package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfScheduleCannotBeAfterEnd
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * A Value Object representing the schedule of a single [ScheduledWorkout].
 *
 * @author Nicola Lasagni on 01/04/2021.
 */
data class Schedule(
    val day: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
) {

    init {
        if (startTime == endTime || endTime.isBefore(startTime)) {
            throw BeginningOfScheduleCannotBeAfterEnd()
        }
    }

    /**
     * Checks if the specified [schedule] overlaps with this Schedule.
     */
    fun overlapsWith(schedule: Schedule): Boolean {
        return hasSameDay(schedule) && (hasSameTimes(schedule) || timesOverlap(schedule))
    }

    private fun hasSameDay(schedule: Schedule): Boolean {
        return day.isEqual(schedule.day)
    }

    private fun hasSameTimes(schedule: Schedule): Boolean {
        return startTime == schedule.startTime && endTime == schedule.endTime
    }

    private fun timesOverlap(schedule: Schedule): Boolean {
        return startTime.isBefore(schedule.endTime) && endTime.isAfter(schedule.startTime)
    }

}
