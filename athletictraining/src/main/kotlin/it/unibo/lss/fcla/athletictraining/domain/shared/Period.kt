package it.unibo.lss.fcla.athletictraining.domain.shared

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.BeginningOfPeriodCannotBeAfterEnd
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * The period of an athletic training.
 *
 * A period is composed by a [LocalDate] which represents its beginning at time [LocalDateTime.MIN]
 * and a [LocalDate] which represents its end at time [LocalDateTime.MAX].
 *
 * @author Nicola Lasagni on 22/02/2021.
 *
 */
data class Period(
    private val beginningDay: LocalDate,
    private val endDay: LocalDate
) {

    private val beginningDateTime: LocalDateTime
    private val endDateTime: LocalDateTime

    init {
        enforceInvariants()
        beginningDateTime = beginningDay.atTime(LocalTime.MIN)
        endDateTime = endDay.atTime(LocalTime.MAX)
    }

    private fun enforceInvariants() {
        if (endDay.isBefore(beginningDay)) {
            throw BeginningOfPeriodCannotBeAfterEnd()
        }
    }

    /**
     * Indicates the day this period begins.
     */
    fun beginningDay() = beginningDay

    /**
     * Indicates the day and time this period begins.
     */
    fun beginningDateTime() = beginningDateTime

    /**
     * Indicates the day this period ends.
     */
    fun endDay() = endDay

    /**
     * Indicates the day and time this period ends.
     */
    fun endDateTime() = endDateTime

    /**
     *
     */
    fun changeEndDay(endDay: LocalDate): Period {
        return Period(beginningDay, endDay)
    }

    /**
     * Checks if the specified [period] overlaps with this Period.
     * @return True if the specified [period] overlaps with this Period, false otherwise.
     */
    fun overlapsWith(period: Period): Boolean {
        return hasSameDay(period) ||
            (beginningDay.isBefore(period.endDay) && endDay.isAfter(period.beginningDay))
    }

    /**
     * Checks if this Period has same [beginningDay] and [endDay] of the specified [period].
     * @return True if this Period has same [beginningDay] and [endDay] of the specified [period], false otherwise.
     */
    private fun hasSameDay(period: Period): Boolean {
        return beginningDay.isEqual(period.beginningDay) && endDay.isEqual(period.endDay)
    }

    /**
     * Checks if this period ends after the specified [period].
     */
    fun endsAfter(period: Period): Boolean {
        return endDay.isAfter(period.endDay)
    }
}
