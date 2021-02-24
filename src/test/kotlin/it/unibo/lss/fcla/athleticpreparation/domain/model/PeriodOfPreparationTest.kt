package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athleticpreparation.domain.exception.PeriodOfPreparationDoesNotMeetMinimumDuration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodOfPreparationTest : FreeSpec({
    "PeriodOfPreparation should" - {
        val validBeginning = LocalDate.of(2020, 12, 1)
        val validEnd = LocalDate.of(2021, 2, 22)
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfPeriodCannotBeAfterEnd> {
                PeriodOfPreparation(validEnd, validBeginning)
            }
            assertDoesNotThrow {
                PeriodOfPreparation(validBeginning, validEnd)
            }
        }
        "be equal to another with same beginning and end" - {
            val periodOne = PeriodOfPreparation(validBeginning, validEnd)
            val periodTwo = PeriodOfPreparation(validBeginning, validEnd)
            Assertions.assertEquals(periodOne, periodTwo)
        }
        "last at least ${PeriodOfPreparation.minimumPeriodDurationInMonth} months" - {
            val invalidBeginning = LocalDate.of(2021, 2, 21)
            val invalidEnd = LocalDate.of(2021, 2, 22)
            assertThrows<PeriodOfPreparationDoesNotMeetMinimumDuration> {
                PeriodOfPreparation(invalidBeginning, invalidEnd)
            }
            assertDoesNotThrow {
                PeriodOfPreparation(validBeginning, validEnd)
            }
        }
    }
})