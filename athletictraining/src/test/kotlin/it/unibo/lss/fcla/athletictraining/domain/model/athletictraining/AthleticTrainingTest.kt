package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.PeriodExtensionCannotEndBeforeCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.PostponedPeriodMustHaveSameBeginningOfCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Tests of the [AthleticTraining] Aggregate Root.
 *
 * @author Nicola Lasagni on 24/02/2021.
 */
class AthleticTrainingTest : FreeSpec({
    lateinit var fakeAthleticTrainerId: AthleticTrainerId
    lateinit var fakeMemberId: MemberId
    lateinit var fakeWorkoutId: WorkoutId
    lateinit var validBeginning: LocalDate
    lateinit var validEnd: LocalDate
    lateinit var validPeriod: Period
    lateinit var validPurpose: Purpose
    lateinit var validAthleticTraining: AthleticTraining
    lateinit var schedule: Schedule

    /**
     * Setup before every test.
     */
    beforeAny {
        fakeAthleticTrainerId = AthleticTrainerId("1234")
        fakeMemberId = MemberId("1234")
        fakeWorkoutId = WorkoutId("1234")
        validBeginning = LocalDate.now()
        validEnd = validBeginning.plusMonths(Period.minimumPeriodDurationInMonth.toLong())
        validPeriod = Period(validBeginning, validEnd)
        validPurpose = Purpose.AthleticPreparation()
        validAthleticTraining = AthleticTraining(
            fakeAthleticTrainerId,
            fakeMemberId,
            validPurpose,
            validPeriod
        )
        schedule = Schedule(
            validBeginning,
            LocalTime.now(),
            LocalTime.now().plusHours(1)
        )
    }

    "An active athletic training should" - {
        "be planned for a member, by an athletic trainer, and have a valid period" - {
            Assertions.assertDoesNotThrow {
                AthleticTraining(
                    fakeAthleticTrainerId,
                    fakeMemberId,
                    validPurpose,
                    validPeriod
                )
            }
            assertThrows<AthleticTrainingMustHaveAthleticTrainer> {
                AthleticTraining(
                    AthleticTrainerId(""),
                    fakeMemberId,
                    validPurpose,
                    validPeriod
                )
            }
            assertThrows<AthleticTrainingMustHaveMember> {
                AthleticTraining(
                    fakeAthleticTrainerId,
                    MemberId(""),
                    validPurpose,
                    validPeriod
                )
            }
        }
        "offer a snapshot of itself" - {
            val athleticTraining = AthleticTraining(
                fakeAthleticTrainerId,
                fakeMemberId,
                validPurpose,
                validPeriod
            )
            val snapshot = athleticTraining.snapshot()
            Assertions.assertEquals(fakeAthleticTrainerId, snapshot.athleticTrainerId)
            Assertions.assertEquals(fakeMemberId, snapshot.memberId)
            Assertions.assertEquals(validPeriod, snapshot.period)
        }
        "allow postponing the training period end" - {
            val postponedEnd = validEnd.plusWeeks(1)
            val postponedPeriod = Period(validBeginning, postponedEnd)
            assertDoesNotThrow {
                validAthleticTraining.postponeTrainingPeriodEnd(postponedPeriod)
            }
            val snapshot = validAthleticTraining.snapshot()
            Assertions.assertEquals(postponedPeriod.end, snapshot.period.end)
        }
        "not allow changing the training period beginning" - {
            val beginning = LocalDate.now().plusMonths(1)
            val end = beginning.plusMonths(Period.minimumPeriodDurationInMonth.toLong())
            val period = Period(beginning, end)
            val athleticTraining = AthleticTraining(
                fakeAthleticTrainerId,
                fakeMemberId,
                validPurpose,
                period
            )
            val postponedBeginning = beginning.minusWeeks(1)
            val postponedPeriod = Period(postponedBeginning, end)
            assertThrows<PostponedPeriodMustHaveSameBeginningOfCurrentPeriod> {
                athleticTraining.postponeTrainingPeriodEnd(postponedPeriod)
            }
        }
        "not allow anticipating the training period end" - {
            val beginning = LocalDate.now().plusMonths(1)
            val end = beginning.plusMonths(Period.minimumPeriodDurationInMonth.toLong() + 1)
            val period = Period(beginning, end)
            val athleticTraining = AthleticTraining(
                fakeAthleticTrainerId,
                fakeMemberId,
                validPurpose,
                period
            )
            val postponedPeriod = Period(beginning, end.minusWeeks(1))
            assertThrows<PeriodExtensionCannotEndBeforeCurrentPeriod> {
                athleticTraining.postponeTrainingPeriodEnd(postponedPeriod)
            }
        }
        "allow to schedule a workout" - {
            assertDoesNotThrow { validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule) }
            val snapshot = validAthleticTraining.snapshot()
            snapshot.scheduledWorkout.shouldHaveSize(1)
        }
        "allow to reschedule an already-scheduled workout" - {
            validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            val newSchedule = Schedule(
                validBeginning,
                LocalTime.now(),
                LocalTime.now().plusHours(2)
            )
            val beforeReschedulingSnapshot = validAthleticTraining.snapshot()
            val scheduledWorkoutId = beforeReschedulingSnapshot.scheduledWorkout.first().id
            assertDoesNotThrow { validAthleticTraining.rescheduleWorkout(scheduledWorkoutId, newSchedule) }
            val postReschedulingSnapshot = validAthleticTraining.snapshot()
            postReschedulingSnapshot.scheduledWorkout.first().schedule.shouldBe(newSchedule)
        }
        "not allow the scheduling of a workout that overlaps with another" - {
            validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            assertThrows<WorkoutScheduleMustNotOverlap> {
                validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            }
        }
        "not allow scheduling of workout out of the period of training" - {
            val invalidScheduleDay = validPeriod.end.plusDays(1).toLocalDate()
            val invalidSchedule = Schedule(
                invalidScheduleDay,
                LocalTime.now(),
                LocalTime.now().plusHours(1)
            )
            assertThrows<WorkoutMustBeScheduledDuringPeriodOfTraining> {
                validAthleticTraining.scheduleWorkout(fakeWorkoutId, invalidSchedule)
            }
        }
        "not allow the scheduling of a workout when completed" - {
            validAthleticTraining.complete()
            assertThrows<AthleticTrainingAlreadyCompleted> {
                validAthleticTraining.scheduleWorkout(fakeWorkoutId, schedule)
            }
        }
    }
})
