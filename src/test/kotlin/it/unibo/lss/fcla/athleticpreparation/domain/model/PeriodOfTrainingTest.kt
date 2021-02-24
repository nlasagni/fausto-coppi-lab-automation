package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athleticpreparation.domain.exception.PeriodOfTrainingDoesNotMeetMinimumDuration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 24/02/2021.
 */
class PeriodOfTrainingTest : FreeSpec({
    "PeriodOfTrainingTest should" - {
        val validBeginning = LocalDate.of(2020, 12, 1)
        val validEnd = LocalDate.of(2021, 2, 22)
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfPeriodCannotBeAfterEnd> {
                PeriodOfTraining(validEnd, validBeginning)
            }
            assertDoesNotThrow {
                PeriodOfTraining(validBeginning, validEnd)
            }
        }
        "be equal to another with same beginning and end" - {
            val periodOne = PeriodOfTraining(validBeginning, validEnd)
            val periodTwo = PeriodOfTraining(validBeginning, validEnd)
            Assertions.assertEquals(periodOne, periodTwo)
        }
        "last at least ${PeriodOfTraining.minimumPeriodDurationInWeeks} weeks" - {
            val invalidBeginning = LocalDate.of(2021, 2, 21)
            val invalidEnd = LocalDate.of(2021, 2, 22)
            assertThrows<PeriodOfTrainingDoesNotMeetMinimumDuration> {
                PeriodOfTraining(invalidBeginning, invalidEnd)
            }
            assertDoesNotThrow {
                PeriodOfTraining(validBeginning, validEnd)
            }
        }
    }
})