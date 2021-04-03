package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.BeginningOfPeriodCannotBeAfterEnd
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

    val beginning: LocalDateTime
    val end: LocalDateTime

    init {
        enforceInvariants()
        beginning = beginningDay.atTime(LocalTime.MIN)
        end = endDay.atTime(LocalTime.MAX)
    }

    private fun enforceInvariants() {
        if (endDay.isBefore(beginningDay)) {
            throw BeginningOfPeriodCannotBeAfterEnd()
        }
    }

    fun hasSameBeginning(period: Period): Boolean {
        return beginningDay.isEqual(period.beginningDay)
    }

    fun endsAfter(period: Period): Boolean {
        return endDay.isAfter(period.endDay)
    }
}
