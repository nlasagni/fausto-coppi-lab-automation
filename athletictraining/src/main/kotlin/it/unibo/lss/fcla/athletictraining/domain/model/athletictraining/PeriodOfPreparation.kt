package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodCannotBeginOrEndBeforeToday
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodOfPreparationDoesNotMeetMinimumDuration
import java.time.LocalDate
import java.time.Period

/**
 * The period of an athletic traning.
 *
 * @see AthleticTraining
 *
 * @author Nicola Lasagni on 22/02/2021.
 *
 * @property beginning A [LocalDate] which represents the beginning of a period.
 * @property end A [LocalDate] which represents the end of a period.
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
