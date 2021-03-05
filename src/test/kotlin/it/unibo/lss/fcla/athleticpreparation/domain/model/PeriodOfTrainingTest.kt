package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.BeginningOfPeriodCannotBeAfterEnd
import it.unibo.lss.fcla.athleticpreparation.domain.exception.PeriodCannotBeginOrEndBeforeToday
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
        val validBeginning = LocalDate.now()
        val validEnd = validBeginning.plusWeeks(PeriodOfTraining.minimumPeriodDurationInWeeks.toLong())
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfPeriodCannotBeAfterEnd> {
                PeriodOfTraining(validEnd, validBeginning)
            }
            assertDoesNotThrow {
                PeriodOfTraining(validBeginning, validEnd)
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
            val periodOne = PeriodOfTraining(validBeginning, validEnd)
            val periodTwo = PeriodOfTraining(validBeginning, validEnd)
            Assertions.assertEquals(periodOne, periodTwo)
        }
        "last at least ${PeriodOfTraining.minimumPeriodDurationInWeeks} weeks" - {
            val invalidEnd = validBeginning.plusWeeks(
                PeriodOfTraining.minimumPeriodDurationInWeeks.toLong() - 1
            )
            assertThrows<PeriodOfTrainingDoesNotMeetMinimumDuration> {
                PeriodOfTraining(validBeginning, invalidEnd)
            }
            assertDoesNotThrow {
                PeriodOfTraining(validBeginning, validEnd)
            }
        }
    }
})
