package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationAlreadyCompleted
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveAthleticTrainer
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveMember
import it.unibo.lss.fcla.athleticpreparation.domain.exception.TrainingPlanMustNotOverlap
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 24/02/2021.
 */
class AthleticPreparationTest : FreeSpec({
    lateinit var fakeAthleticTrainerId: String
    lateinit var fakeMemberId: String
    lateinit var validBeginning: LocalDate
    lateinit var validEnd: LocalDate
    lateinit var validPeriod: PeriodOfPreparation

    beforeAny {
        fakeAthleticTrainerId = "1234"
        fakeMemberId = "1234"
        validBeginning = LocalDate.now()
        validEnd = validBeginning.plusMonths(PeriodOfPreparation.minimumPeriodDurationInMonth.toLong())
        validPeriod = PeriodOfPreparation(validBeginning, validEnd)
    }

    "An active athletic preparation should" - {
        "be planned for a member, by an athletic trainer, and have a valid period" - {
            Assertions.assertDoesNotThrow {
                AthleticPreparation(
                    fakeAthleticTrainerId,
                    fakeMemberId,
                    validPeriod
                )
            }
            assertThrows<AthleticPreparationMustHaveAthleticTrainer> {
                AthleticPreparation(
                    "",
                    fakeMemberId,
                    validPeriod
                )
            }
            assertThrows<AthleticPreparationMustHaveMember> {
                AthleticPreparation(
                    fakeAthleticTrainerId,
                    "",
                    validPeriod
                )
            }
        }
        "offer a snapshot of itself" - {
            val athleticPreparation = AthleticPreparation(
                fakeAthleticTrainerId,
                fakeMemberId,
                validPeriod
            )
            val snapshot = athleticPreparation.snapshot()
            Assertions.assertEquals(fakeAthleticTrainerId, snapshot.athleticTrainerId)
            Assertions.assertEquals(fakeMemberId, snapshot.memberId)
            Assertions.assertEquals(validPeriod, snapshot.periodOfPreparation)
        }
        "allow the preparation of a TrainingPlan" - {
            val athleticPreparation = AthleticPreparation(
                fakeAthleticTrainerId,
                fakeMemberId,
                PeriodOfPreparation(validBeginning, validEnd)
            )
            val trainingPlan = TrainingPlan(
                "Strengthening Training Plan",
                Purpose.Strengthening(),
                PeriodOfTraining(validBeginning, validEnd)
            )
            assertDoesNotThrow { athleticPreparation.prepareTrainingPlan(trainingPlan) }
        }
        "not allow the preparation of a TrainingPlan that overlaps with another" - {
            val athleticPreparation = AthleticPreparation(
                fakeAthleticTrainerId,
                fakeMemberId,
                PeriodOfPreparation(validBeginning, validEnd)
            )
            val trainingPlan = TrainingPlan(
                "Strengthening Training Plan",
                Purpose.Strengthening(),
                PeriodOfTraining(validBeginning, validEnd)
            )
            athleticPreparation.prepareTrainingPlan(trainingPlan)
            val overlappingTrainingPlan = TrainingPlan(
                "Overlapping Training Plan",
                Purpose.Strengthening(),
                PeriodOfTraining(validBeginning, validEnd)
            )
            assertThrows<TrainingPlanMustNotOverlap> {
                athleticPreparation.prepareTrainingPlan(overlappingTrainingPlan)
            }
        }
        "not allow the preparation of a TrainingPlan when completed" - {
            val athleticPreparation = AthleticPreparation(
                fakeAthleticTrainerId,
                fakeMemberId,
                PeriodOfPreparation(validBeginning, validEnd)
            )
            athleticPreparation.complete()
            val trainingPlan = TrainingPlan(
                "Strengthening Training Plan",
                Purpose.Strengthening(),
                PeriodOfTraining(validBeginning, validEnd)
            )
            assertThrows<AthleticPreparationAlreadyCompleted> {
                athleticPreparation.prepareTrainingPlan(trainingPlan)
            }
        }
    }
})
