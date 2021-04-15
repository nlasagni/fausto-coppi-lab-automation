package it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.exception.WorkoutIdMissing
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Tests of the [CompletedWorkout] Entity.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class CompletedWorkoutTest : FreeSpec({

    val tenOClock = LocalTime.of(10, 0)

    lateinit var workoutId: WorkoutId
    lateinit var schedule: Schedule

    beforeAny {
        workoutId = WorkoutId("1234")
        schedule = Schedule(
            LocalDate.now(),
            tenOClock,
            tenOClock.plusHours(1)
        )
    }

    "A completed workout should" - {
        "refer to a workout" - {
            assertThrows<WorkoutIdMissing> {
                CompletedWorkout(WorkoutId(""), schedule)
            }
            assertDoesNotThrow { CompletedWorkout(workoutId, schedule) }
        }
    }
})
