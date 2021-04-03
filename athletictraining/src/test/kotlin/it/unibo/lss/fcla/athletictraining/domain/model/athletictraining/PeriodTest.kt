package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodCannotBeginOrEndBeforeToday
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodDoesNotMeetMinimumDuration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * Tests of the [Period] Value Object.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodTest : FreeSpec({
    "A period should" - {
        val validBeginning = LocalDate.now()
        val validEnd = validBeginning.plusMonths(Period.minimumPeriodDurationInMonth.toLong())
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfPeriodCannotBeAfterEnd> {
                Period(validEnd, validBeginning)
            }
            assertDoesNotThrow {
                Period(validBeginning, validEnd)
            }
        }
        "prevent beginning before today" - {
            val invalidBeginning = LocalDate.now().minusDays(1)
            val invalidEnd = LocalDate.now().minusDays(1)
            assertThrows<PeriodCannotBeginOrEndBeforeToday> {
                Period(validBeginning, invalidEnd)
            }
            assertThrows<PeriodCannotBeginOrEndBeforeToday> {
                Period(invalidBeginning, validEnd)
            }
        }
        "be equal to another with same beginning and end" - {
            val periodOne = Period(validBeginning, validEnd)
            val periodTwo = Period(validBeginning, validEnd)
            Assertions.assertEquals(periodOne, periodTwo)
        }
        "last at least ${Period.minimumPeriodDurationInMonth} months" - {
            val invalidEnd = validBeginning.plusMonths(
                Period.minimumPeriodDurationInMonth.toLong() - 1
            )
            assertThrows<PeriodDoesNotMeetMinimumDuration> {
                Period(validBeginning, invalidEnd)
            }
            assertDoesNotThrow {
                Period(validBeginning, validEnd)
            }
        }
    }
})
