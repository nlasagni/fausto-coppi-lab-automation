package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athleticpreparation.domain.exception.PeriodOfTrainingDoesNotMeetMinimumDuration
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * A value object representing a period of training during an athletic preparation.
 *
 * @property [beginning] A [LocalDate] which represents the beginning of a period.
 * @property [end] A [LocalDate] which represents the end of a period.
 *
 * @see [ActiveAthleticPreparation]
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
data class PeriodOfTraining(val beginning: LocalDate, val end: LocalDate) {

    companion object PeriodOfTraining {
        const val minimumPeriodDurationInWeeks: Int = 4
    }

    init {
        if (end.isBefore(beginning)) {
            throw BeginningOfPeriodCannotBeAfterEnd()
        }
        if (doesNotMeetMinimumPreparationPeriodDuration()) {
            throw PeriodOfTrainingDoesNotMeetMinimumDuration()
        }
    }

    private fun doesNotMeetMinimumPreparationPeriodDuration(): Boolean =
            ChronoUnit.WEEKS.between(beginning, end) < minimumPeriodDurationInWeeks
}