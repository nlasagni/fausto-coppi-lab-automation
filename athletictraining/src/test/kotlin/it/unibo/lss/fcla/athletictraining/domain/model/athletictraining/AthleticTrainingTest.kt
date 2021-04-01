package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutMustBeScheduledDuringPeriodOfPreparation
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Tests of the [AthleticTraining] domain Entity.
 *
 * @author Nicola Lasagni on 24/02/2021.
 */
class AthleticTrainingTest : FreeSpec({
    lateinit var fakeAthleticTrainerId: AthleticTrainerId
    lateinit var fakeMemberId: MemberId
    lateinit var fakeWorkoutId: WorkoutId
    lateinit var validBeginning: LocalDate
    lateinit var validEnd: LocalDate
    lateinit var validPeriod: PeriodOfPreparation
    lateinit var validAthleticTraining: AthleticTraining

    /**
     * Setup before every test.
     */
    beforeAny {
        fakeAthleticTrainerId = AthleticTrainerId("1234")
        fakeMemberId = MemberId("1234")
        fakeWorkoutId = WorkoutId("1234")
        validBeginning = LocalDate.now()
        validEnd = validBeginning.plusMonths(PeriodOfPreparation.minimumPeriodDurationInMonth.toLong())
        validPeriod = PeriodOfPreparation(validBeginning, validEnd)
        validAthleticTraining = AthleticTraining(
            fakeAthleticTrainerId,
            fakeMemberId,
            validPeriod
        )
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
            val athleticTraining = AthleticTraining(
                fakeAthleticTrainerId,
                fakeMemberId,
                validPeriod
            )
            val snapshot = athleticTraining.snapshot()
            Assertions.assertEquals(fakeAthleticTrainerId, snapshot.athleticTrainerId)
            Assertions.assertEquals(fakeMemberId, snapshot.memberId)
            Assertions.assertEquals(validPeriod, snapshot.periodOfPreparation)
        }
        "allow the schedule of a workout" - {
            val schedule = Schedule(
                validBeginning,
                LocalTime.now(),
                LocalTime.now().plusHours(1)
            )
            assertDoesNotThrow { validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule) }
        }
        "not allow the scheduling of a workout that overlaps with another" - {
            val schedule = Schedule(
                validBeginning,
                LocalTime.now(),
                LocalTime.now().plusHours(1)
            )
            validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            assertThrows<WorkoutScheduleMustNotOverlap> {
                validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            }
        }
        "not allow scheduling of workout out of the period of training" - {
            val schedule = Schedule(
                validEnd.plusDays(1),
                LocalTime.now(),
                LocalTime.now().plusHours(1)
            )
            assertThrows<WorkoutMustBeScheduledDuringPeriodOfPreparation> {
                validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            }
        }
        "not allow the scheduling of a workout when completed" - {
            validAthleticTraining.complete()
            val schedule = Schedule(
                validBeginning,
                LocalTime.now(),
                LocalTime.now().plusHours(1)
            )
            assertThrows<AthleticTrainingAlreadyCompleted> {
                validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            }
        }
    }
})
