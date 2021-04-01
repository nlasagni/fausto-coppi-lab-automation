package it.unibo.lss.fcla.athletictraining.domain.model.workout

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Test of the [Workout] domain Entity.
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
class WorkoutTest : FreeSpec({
    lateinit var workoutName: String
    lateinit var today: LocalDate
    lateinit var todayTime: LocalTime

    /**
     * Setup before every test.
     */
    beforeAny {
        workoutName = "Workout"
        today = LocalDate.now()
        todayTime = LocalTime.now()
    }

    "Workout should" - {
        "have a name" - {
            assertThrows<NameMustNotBeEmpty> {
                Workout("", today, todayTime)
            }
        }
        "offer a snapshot of itself" - {
            val workout = Workout(workoutName, today, todayTime)
            val snapshot = workout.snapshot()
            Assertions.assertEquals(workoutName, snapshot.name)
            Assertions.assertEquals(today, snapshot.day)
            Assertions.assertEquals(todayTime, snapshot.time)
        }
        "allow the preparation of exercises" - {
            val workout = Workout(workoutName, today, todayTime)
            val exerciseId = ExerciseId("1234")
            assertDoesNotThrow {
                workout.prepareExercise(exerciseId)
            }
            val snapshot = workout.snapshot()
            Assertions.assertEquals(snapshot.exercises.size, 1)
        }
    }
})
