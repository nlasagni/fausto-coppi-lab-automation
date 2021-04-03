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

    companion object PeriodOfPreparation {
        const val minimumPeriodDurationInMonth: Int = 2
    }

    val beginning: LocalDateTime
    val end: LocalDateTime

    init {
        enforceInvariants()
        beginning = beginningDay.atTime(LocalTime.MIN)
        end = endDay.atTime(LocalTime.MAX)
    }

    private fun enforceInvariants() {
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
