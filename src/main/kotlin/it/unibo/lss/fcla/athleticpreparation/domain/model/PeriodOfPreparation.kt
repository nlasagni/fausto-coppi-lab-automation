package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athleticpreparation.domain.exception.PeriodCannotBeginOrEndBeforeToday
import it.unibo.lss.fcla.athleticpreparation.domain.exception.PeriodOfPreparationDoesNotMeetMinimumDuration
import java.time.LocalDate
import java.time.Period

/**
 * A value object representing the period of an athletic preparation.
 *
 * @see AthleticPreparation
 *
 * @author Nicola Lasagni on 22/02/2021.
 *
 * @property beginning
 * @property end
 */
data class PeriodOfPreparation(val beginning: LocalDate, val end: LocalDate) {

    companion object PeriodOfPreparation {
        const val minimumPeriodDurationInMonth: Int = 2
    }

    init {
        val now = LocalDate.now()
        if (beginning.isBefore(now) || end.isBefore(now)) {
            throw PeriodCannotBeginOrEndBeforeToday()
        }
        if (end.isBefore(beginning)) {
            throw BeginningOfPeriodCannotBeAfterEnd()
        }
        if (doesNotMeetMinimumPreparationPeriodDuration()) {
            throw PeriodOfPreparationDoesNotMeetMinimumDuration()
        }
    }

    private fun doesNotMeetMinimumPreparationPeriodDuration(): Boolean =
        Period.between(beginning, end).months < minimumPeriodDurationInMonth
}
