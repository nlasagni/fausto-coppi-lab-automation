package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.WorkoutIdMissing
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Test of the [ScheduledWorkout] Entity.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ScheduledWorkoutTest : FreeSpec({

    val tenOClock = LocalTime.of(10, 0)

    lateinit var workoutId: WorkoutId
    lateinit var schedule: Schedule
    lateinit var scheduledWorkout: ScheduledWorkout

    beforeAny {
        workoutId = WorkoutId("1234")
        schedule = Schedule(
            LocalDate.now(),
            tenOClock,
            tenOClock.plusHours(1)
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
                tenOClock,
                tenOClock.plusHours(2)
            )
            scheduledWorkout.reschedule(newSchedule)
            scheduledWorkout.scheduledOn().shouldBe(newSchedule)
        }
    }
})
