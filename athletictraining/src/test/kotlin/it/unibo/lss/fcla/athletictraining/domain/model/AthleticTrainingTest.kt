package it.unibo.lss.fcla.athletictraining.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.exception.TrainingPlanMustNotOverlap
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * Tests of the [AthleticTraining] domain Entity.
 *
 * @author Nicola Lasagni on 24/02/2021.
 */
class AthleticTrainingTest : FreeSpec({
    lateinit var fakeAthleticTrainerId: AthleticTrainerId
    lateinit var fakeMemberId: MemberId
    lateinit var validBeginning: LocalDate
    lateinit var validEnd: LocalDate
    lateinit var validPeriod: PeriodOfPreparation

    /**
     * Setup before every test.
     */
    beforeAny {
        fakeAthleticTrainerId = AthleticTrainerId("1234")
        fakeMemberId = MemberId("1234")
        validBeginning = LocalDate.now()
        validEnd = validBeginning.plusMonths(PeriodOfPreparation.minimumPeriodDurationInMonth.toLong())
        validPeriod = PeriodOfPreparation(validBeginning, validEnd)
    }

    "An active athletic training should" - {
        "be planned for a member, by an athletic trainer, and have a valid period" - {
            Assertions.assertDoesNotThrow {
                AthleticTraining(
                    fakeAthleticTrainerId,
                    fakeMemberId,
                    validPeriod
                )
            }
            assertThrows<AthleticTrainingMustHaveAthleticTrainer> {
                AthleticTraining(
                    AthleticTrainerId(""),
                    fakeMemberId,
                    validPeriod
                )
            }
            assertThrows<AthleticTrainingMustHaveMember> {
                AthleticTraining(
                    fakeAthleticTrainerId,
                    MemberId(""),
                    validPeriod
                )
            }
        }
        "offer a snapshot of itself" - {
            val athleticPreparation = AthleticTraining(
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
            val athleticPreparation = AthleticTraining(
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
            val athleticPreparation = AthleticTraining(
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
            val athleticPreparation = AthleticTraining(
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
            assertThrows<AthleticTrainingAlreadyCompleted> {
                athleticPreparation.prepareTrainingPlan(trainingPlan)
            }
        }
    }
})
