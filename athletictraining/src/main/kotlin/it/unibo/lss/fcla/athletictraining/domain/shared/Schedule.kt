/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.shared

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.BeginningOfScheduleCannotBeAfterEnd
import java.time.LocalDate
import java.time.LocalTime

/**
 * A Value Object representing the schedule of a single ScheduledWorkout.
 *
 * @property day The day of the schedule.
 * @property startTime The start time of this schedule.
 * @property endTime The end time of this schedule.
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
