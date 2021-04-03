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
     * @return True if the specified [schedule] overlaps with this Schedule, false otherwise.
     */
    fun overlapsWith(schedule: Schedule): Boolean {
        return hasSameDay(schedule) && (hasSameTimes(schedule) || timesOverlap(schedule))
    }

    /**
     * Checks if this Schedule has same [day] of the specified [schedule].
     * @return True if this Schedule has same [day] of the specified [schedule], false otherwise.
     */
    private fun hasSameDay(schedule: Schedule): Boolean {
        return day.isEqual(schedule.day)
    }

    /**
     * Checks if this Schedule has same [startTime] and [endTime]
     * of the specified [schedule].
     * @return True if this Schedule has same [startTime] and [endTime]
     * of the specified [schedule], false otherwise.
     */
    private fun hasSameTimes(schedule: Schedule): Boolean {
        return startTime == schedule.startTime && endTime == schedule.endTime
    }

    /**
     * Checks if times of this Schedule overlaps with the ones
     * of the specified [schedule].
     * @return True if times of this Schedule overlaps with the ones
     * of the specified [schedule].
     */
    private fun timesOverlap(schedule: Schedule): Boolean {
        return startTime.isBefore(schedule.endTime) && endTime.isAfter(schedule.startTime)
    }

}
