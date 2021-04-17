/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.PeriodExtensionCannotEndBeforeCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveMember
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Tests of the [ActiveAthleticTraining] Aggregate Root.
 *
 * @author Nicola Lasagni on 24/02/2021.
 */
class ActiveAthleticTrainingTest : FreeSpec({

    val tenOClock = LocalTime.of(10, 0)

    lateinit var activeAthleticTrainingId: ActiveAthleticTrainingId
    lateinit var athleticTrainer: AthleticTrainerId
    lateinit var member: MemberId
    lateinit var workout: WorkoutId
    lateinit var beginning: LocalDate
    lateinit var end: LocalDate
    lateinit var period: Period
    lateinit var purpose: Purpose
    lateinit var activeAthleticTraining: ActiveAthleticTraining
    lateinit var schedule: Schedule

    beforeAny {
        activeAthleticTrainingId = ActiveAthleticTrainingId(UuidGenerator().generate())
        athleticTrainer = AthleticTrainerId("1234")
        member = MemberId("1234")
        workout = WorkoutId("1234")
        beginning = LocalDate.now()
        end = beginning.plusMonths(1)
        period = Period(beginning, end)
        purpose = Purpose("Athletic Preparation")
        activeAthleticTraining = ActiveAthleticTraining(
            activeAthleticTrainingId,
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
                ActiveAthleticTraining(
                    activeAthleticTrainingId,
                    athleticTrainer,
                    member,
                    purpose,
                    period
                )
            }
            assertThrows<AthleticTrainingMustHaveAthleticTrainer> {
                ActiveAthleticTraining(
                    activeAthleticTrainingId,
                    AthleticTrainerId(""),
                    member,
                    purpose,
                    period
                )
            }
            assertThrows<AthleticTrainingMustHaveMember> {
                ActiveAthleticTraining(
                    activeAthleticTrainingId,
                    athleticTrainer,
                    MemberId(""),
                    purpose,
                    period
                )
            }
        }
        "offer a snapshot of itself" - {
            val snapshot = activeAthleticTraining.snapshot()
            Assertions.assertEquals(athleticTrainer, snapshot.athleticTrainer)
            Assertions.assertEquals(member, snapshot.member)
            Assertions.assertEquals(period, snapshot.period)
        }
        "be able to check if it overlaps with another training period" - {
            activeAthleticTraining.overlapsWithPeriod(period).shouldBeTrue()
        }
        "allow postponing the training period end" - {
            val postponedEnd = end.plusWeeks(1)
            assertDoesNotThrow {
                activeAthleticTraining.postponeTrainingPeriodEnd(postponedEnd)
            }
            val snapshot = activeAthleticTraining.snapshot()
            Assertions.assertEquals(postponedEnd, snapshot.period.endDay())
        }
        "not allow anticipating the training period end" - {
            val invalidPeriod = end.minusWeeks(1)
            assertThrows<PeriodExtensionCannotEndBeforeCurrentPeriod> {
                activeAthleticTraining.postponeTrainingPeriodEnd(invalidPeriod)
            }
        }
        "allow to schedule a workout" - {
            assertDoesNotThrow { activeAthleticTraining.scheduleWorkout(workout, schedule) }
            val snapshot = activeAthleticTraining.snapshot()
            snapshot.scheduledWorkouts.shouldHaveSize(1)
        }
        "allow to reschedule an already-scheduled workout" - {
            activeAthleticTraining.scheduleWorkout(workout, schedule)
            val newSchedule = Schedule(
                beginning,
                tenOClock,
                tenOClock.plusHours(2)
            )
            val beforeReschedulingSnapshot = activeAthleticTraining.snapshot()
            val scheduledWorkout = beforeReschedulingSnapshot.scheduledWorkouts.first()
            assertDoesNotThrow {
                activeAthleticTraining.rescheduleWorkout(
                    scheduledWorkout.workout,
                    scheduledWorkout.schedule,
                    newSchedule
                )
            }
            val postReschedulingSnapshot = activeAthleticTraining.snapshot()
            postReschedulingSnapshot.scheduledWorkouts.first().schedule.shouldBe(newSchedule)
        }
        "allow to cancel a scheduled workout" - {
            activeAthleticTraining.scheduleWorkout(workout, schedule)
            val beforeReschedulingSnapshot = activeAthleticTraining.snapshot()
            val scheduledWorkout = beforeReschedulingSnapshot.scheduledWorkouts.first()
            assertDoesNotThrow {
                activeAthleticTraining.cancelScheduledWorkout(
                    scheduledWorkout.workout,
                    scheduledWorkout.schedule
                )
            }
            val postCancellationSnapshot = activeAthleticTraining.snapshot()
            postCancellationSnapshot.scheduledWorkouts.shouldBeEmpty()
        }
        "not allow the scheduling of a workout that overlaps with another" - {
            activeAthleticTraining.scheduleWorkout(workout, schedule)
            assertThrows<WorkoutScheduleMustNotOverlap> {
                activeAthleticTraining.scheduleWorkout(workout, schedule)
            }
        }
        "not allow scheduling of workout out of the period of training" - {
            val invalidScheduleDay = period.endDay().plusDays(1)
            val invalidSchedule = Schedule(
                invalidScheduleDay,
                tenOClock,
                tenOClock.plusHours(1)
            )
            assertThrows<WorkoutMustBeScheduledDuringPeriodOfTraining> {
                activeAthleticTraining.scheduleWorkout(workout, invalidSchedule)
            }
        }
    }
})
