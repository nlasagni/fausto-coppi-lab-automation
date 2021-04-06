package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
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

    val tenOClock = LocalTime.of(10, 0)

    lateinit var athleticTrainingId: AthleticTrainingId
    lateinit var athleticTrainer: AthleticTrainerId
    lateinit var member: MemberId
    lateinit var workout: WorkoutId
    lateinit var beginning: LocalDate
    lateinit var end: LocalDate
    lateinit var period: Period
    lateinit var purpose: Purpose
    lateinit var athleticTraining: AthleticTraining
    lateinit var schedule: Schedule

    /**
     * Setup before every test.
     */
    beforeAny {
        athleticTrainingId = AthleticTrainingId(UuidGenerator().generate())
        athleticTrainer = AthleticTrainerId("1234")
        member = MemberId("1234")
        workout = WorkoutId("1234")
        beginning = LocalDate.now()
        end = beginning.plusMonths(1)
        period = Period(beginning, end)
        purpose = Purpose.AthleticPreparation()
        athleticTraining = AthleticTraining(
            athleticTrainingId,
            athleticTrainer,
            member,
            purpose,
            period
        )
        schedule = Schedule(
            beginning,
            tenOClock,
            tenOClock.plusHours(1)
        )
    }

    "An active athletic training should" - {
        "be planned for a member, by an athletic trainer, and have a valid period" - {
            Assertions.assertDoesNotThrow {
                AthleticTraining(
                    athleticTrainingId,
                    athleticTrainer,
                    member,
                    purpose,
                    period
                )
            }
            assertThrows<AthleticTrainingMustHaveAthleticTrainer> {
                AthleticTraining(
                    athleticTrainingId,
                    AthleticTrainerId(""),
                    member,
                    purpose,
                    period
                )
            }
            assertThrows<AthleticTrainingMustHaveMember> {
                AthleticTraining(
                    athleticTrainingId,
                    athleticTrainer,
                    MemberId(""),
                    purpose,
                    period
                )
            }
        }
        "offer a snapshot of itself" - {
            val snapshot = athleticTraining.snapshot()
            Assertions.assertEquals(athleticTrainer, snapshot.athleticTrainerId)
            Assertions.assertEquals(member, snapshot.memberId)
            Assertions.assertEquals(period, snapshot.period)
        }
        "allow postponing the training period end" - {
            val postponedEnd = end.plusWeeks(1)
            val postponedPeriod = Period(beginning, postponedEnd)
            assertDoesNotThrow {
                athleticTraining.postponeTrainingPeriodEnd(postponedPeriod)
            }
            val snapshot = athleticTraining.snapshot()
            Assertions.assertEquals(postponedPeriod.end, snapshot.period.end)
        }
        "not allow changing the training period beginning" - {
            val invalidBeginning = beginning.minusWeeks(1)
            val postponedPeriod = Period(invalidBeginning, end)
            assertThrows<PostponedPeriodMustHaveSameBeginningOfCurrentPeriod> {
                athleticTraining.postponeTrainingPeriodEnd(postponedPeriod)
            }
        }
        "not allow anticipating the training period end" - {
            val invalidPeriod = Period(beginning, end.minusWeeks(1))
            assertThrows<PeriodExtensionCannotEndBeforeCurrentPeriod> {
                athleticTraining.postponeTrainingPeriodEnd(invalidPeriod)
            }
        }
        "allow to schedule a workout" - {
            assertDoesNotThrow { athleticTraining.scheduleWorkout(workout, schedule) }
            val snapshot = athleticTraining.snapshot()
            snapshot.scheduledWorkout.shouldHaveSize(1)
        }
        "allow to reschedule an already-scheduled workout" - {
            athleticTraining.scheduleWorkout(workout, schedule)
            val newSchedule = Schedule(
                beginning,
                tenOClock,
                tenOClock.plusHours(2)
            )
            val beforeReschedulingSnapshot = athleticTraining.snapshot()
            val scheduledWorkoutId = beforeReschedulingSnapshot.scheduledWorkout.first().id
            assertDoesNotThrow { athleticTraining.rescheduleWorkout(scheduledWorkoutId, newSchedule) }
            val postReschedulingSnapshot = athleticTraining.snapshot()
            postReschedulingSnapshot.scheduledWorkout.first().schedule.shouldBe(newSchedule)
        }
        "not allow the scheduling of a workout that overlaps with another" - {
            athleticTraining.scheduleWorkout(workout, schedule)
            assertThrows<WorkoutScheduleMustNotOverlap> {
                athleticTraining.scheduleWorkout(workout, schedule)
            }
        }
        "not allow scheduling of workout out of the period of training" - {
            val invalidScheduleDay = period.end.plusDays(1).toLocalDate()
            val invalidSchedule = Schedule(
                invalidScheduleDay,
                tenOClock,
                tenOClock.plusHours(1)
            )
            assertThrows<WorkoutMustBeScheduledDuringPeriodOfTraining> {
                athleticTraining.scheduleWorkout(workout, invalidSchedule)
            }
        }
        "not allow the scheduling of a workout when completed" - {
            athleticTraining.complete()
            assertThrows<AthleticTrainingAlreadyCompleted> {
                athleticTraining.scheduleWorkout(workout, schedule)
            }
        }
    }
})
