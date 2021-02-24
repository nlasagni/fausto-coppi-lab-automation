package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveAthleticTrainer
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveMember
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 24/02/2021.
 */
class ActiveAthleticPreparationTest : FreeSpec({
    "An active athletic preparation should" - {
        "be planned for a member and by an athletic trainer and have a valid period" - {
            val fakeAthleticTrainerId = "1234"
            val fakeMemberId = "1234"
            val validBeginning = LocalDate.of(2020, 12, 1)
            val validEnd = LocalDate.of(2021, 2, 22)
            Assertions.assertDoesNotThrow {
                ActiveAthleticPreparation(
                        fakeAthleticTrainerId,
                        fakeMemberId,
                        PeriodOfPreparation(validBeginning, validEnd)
                )
            }
            assertThrows<AthleticPreparationMustHaveAthleticTrainer> {
                ActiveAthleticPreparation(
                        "",
                        fakeMemberId,
                        PeriodOfPreparation(validBeginning, validEnd)
                )
            }
            assertThrows<AthleticPreparationMustHaveMember> {
                ActiveAthleticPreparation(
                        fakeAthleticTrainerId,
                        "",
                        PeriodOfPreparation(validBeginning, validEnd)
                )
            }
        }
    }
})