package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Test of the [ScheduledWorkout] Entity.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ScheduledWorkoutTest : FreeSpec({

    lateinit var workoutId: WorkoutId
    lateinit var schedule: Schedule
    lateinit var scheduledWorkout: ScheduledWorkout

    beforeAny {
        workoutId = WorkoutId("1234")
        schedule = Schedule(
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(1)
        )
        scheduledWorkout = ScheduledWorkout(workoutId, schedule)
    }

    "A scheduled workout should" - {
        "refer to a workout" - {
            assertThrows<WorkoutIdMissing> {
                ScheduledWorkout(WorkoutId(""), schedule)
            }
        }
        "allow to be rescheduled" - {
            val newSchedule = Schedule(
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now().plusHours(2)
            )
            scheduledWorkout.reschedule(newSchedule)
            val snapshot = scheduledWorkout.snapshot()
            snapshot.schedule.shouldBe(newSchedule)
        }
        "be able to check if it overlaps with another" - {
            val overlappingScheduledWorkout = ScheduledWorkout(workoutId, schedule)
            scheduledWorkout.overlapsWith(overlappingScheduledWorkout).shouldBeTrue()
        }
    }
})