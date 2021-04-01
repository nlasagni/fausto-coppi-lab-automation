package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodCannotBeginOrEndBeforeToday
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodDoesNotMeetMinimumDuration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
/**
 * The period of an [AthleticTraining].
 *
 * @see AthleticTraining
 *
 * @author Nicola Lasagni on 22/02/2021.
 *
 * @property beginning A [LocalDateTime] which represents the beginning at time [LocalDateTime.MIN] of a period.
 * @property end A [LocalDateTime] which represents the end at time [LocalDateTime.MAX] of a period.
 */
data class Period(
    private val beginningDay: LocalDate,
    private val endDay: LocalDate
) {

    companion object PeriodOfPreparation {
        const val minimumPeriodDurationInMonth: Int = 2
    }

    val beginning: LocalDateTime
    val end: LocalDateTime

    init {
        val now = LocalDate.now()
        if (beginningDay.isBefore(now) || endDay.isBefore(now)) {
            throw PeriodCannotBeginOrEndBeforeToday()
        }
        if (endDay.isBefore(beginningDay)) {
            throw BeginningOfPeriodCannotBeAfterEnd()
        }
        if (doesNotMeetMinimumPreparationPeriodDuration()) {
            throw PeriodDoesNotMeetMinimumDuration()
        }
        beginning = beginningDay.atTime(LocalTime.MIN)
        end = endDay.atTime(LocalTime.MAX)
    }

    fun hasSameBeginning(period: Period): Boolean {
        return beginningDay.isEqual(period.beginningDay)
    }

    fun endsAfter(period: Period): Boolean {
        return endDay.isAfter(period.endDay)
    }

    private fun doesNotMeetMinimumPreparationPeriodDuration(): Boolean =
        java.time.Period.between(beginningDay, endDay).months < minimumPeriodDurationInMonth
}
