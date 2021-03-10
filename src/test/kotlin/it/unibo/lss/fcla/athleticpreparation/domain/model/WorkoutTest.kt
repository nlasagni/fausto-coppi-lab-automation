package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.ExceededMaximumWorkoutDuration
import it.unibo.lss.fcla.athleticpreparation.domain.exception.NameMustNotBeEmpty
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Nicola Lasagni on 04/03/2021.
 */
class WorkoutTest : FreeSpec({
    lateinit var workoutName: String
    lateinit var today: LocalDate
    lateinit var todayTime: LocalTime

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
            val configuration = Configuration("")
            val exercise = Exercise(configuration, Duration.ZERO, Duration.ZERO)
            assertDoesNotThrow {
                workout.prepareExercise(exercise)
            }
            val snapshot = workout.snapshot()
            Assertions.assertEquals(snapshot.exercises.size, 1)
        }
        "not allow the prepared exercises to exceed the permitted duration" - {
            val workout = Workout(workoutName, today, todayTime)
            val configuration = Configuration("")
            val exerciseDuration = Duration.ofHours(3)
            val exercise = Exercise(configuration, exerciseDuration, exerciseDuration)
            assertThrows<ExceededMaximumWorkoutDuration> {
                workout.prepareExercise(exercise)
            }
        }
    }
})
