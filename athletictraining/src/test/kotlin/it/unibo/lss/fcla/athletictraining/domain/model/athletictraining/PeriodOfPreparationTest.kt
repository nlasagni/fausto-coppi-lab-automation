package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodCannotBeginOrEndBeforeToday
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodOfPreparationDoesNotMeetMinimumDuration
import it.unibo.lss.fcla.athletictraining.domain.model.PeriodOfTraining
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * Tests of the [PeriodOfPreparation] Value Object.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodOfPreparationTest : FreeSpec({
    "PeriodOfPreparation should" - {
        val validBeginning = LocalDate.now()
        val validEnd = validBeginning.plusMonths(PeriodOfPreparation.minimumPeriodDurationInMonth.toLong())
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfPeriodCannotBeAfterEnd> {
                PeriodOfPreparation(validEnd, validBeginning)
            }
            assertDoesNotThrow {
                PeriodOfPreparation(validBeginning, validEnd)
            }
        }
        "prevent beginning before today" - {
            val invalidBeginning = LocalDate.now().minusDays(1)
            val invalidEnd = LocalDate.now().minusDays(1)
            assertThrows<PeriodCannotBeginOrEndBeforeToday> {
                PeriodOfTraining(validBeginning, invalidEnd)
            }
            assertThrows<PeriodCannotBeginOrEndBeforeToday> {
                PeriodOfTraining(invalidBeginning, validEnd)
            }
        }
        "be equal to another with same beginning and end" - {
            val periodOne = PeriodOfPreparation(validBeginning, validEnd)
            val periodTwo = PeriodOfPreparation(validBeginning, validEnd)
            Assertions.assertEquals(periodOne, periodTwo)
        }
        "last at least ${PeriodOfPreparation.minimumPeriodDurationInMonth} months" - {
            val invalidEnd = validBeginning.plusMonths(
                PeriodOfPreparation.minimumPeriodDurationInMonth.toLong() - 1
            )
            assertThrows<PeriodOfPreparationDoesNotMeetMinimumDuration> {
                PeriodOfPreparation(validBeginning, invalidEnd)
            }
            assertDoesNotThrow {
                PeriodOfPreparation(validBeginning, validEnd)
            }
        }
    }
})
