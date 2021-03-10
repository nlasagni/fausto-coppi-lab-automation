package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.ExceededMaximumWorkoutDuration
import it.unibo.lss.fcla.athleticpreparation.domain.exception.NameMustNotBeEmpty
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Nicola Lasagni on 25/02/2021.
 */
class Workout(
    private val name: String,
    private val day: LocalDate,
    private val time: LocalTime
) {

    val maximumWorkoutDuration: Duration = Duration.ofHours(4)

    private val id: WorkoutId
    private var exercises: List<Exercise> = emptyList()

    init {
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        id = generateId()
    }

    private fun generateId() = WorkoutId("$name-$day-$time")

    fun snapshot(): WorkoutSnapshot = WorkoutSnapshot(name, day, time, exercises)

    fun prepareExercise(exercise: Exercise) {
        if (exceedMaximumDuration(exercise)) {
           throw ExceededMaximumWorkoutDuration()
        }
        exercises = exercises + exercise
    }

    private fun exceedMaximumDuration(exercise: Exercise) : Boolean {
        val currentDurationInMinutes = exercises.map {
            it.durationOfExecution.toMinutes() + it.durationOfRest.toMinutes()
        }.foldRight(Duration.ZERO.toMinutes()) {
            ex1Duration, ex2Duration -> ex1Duration + ex2Duration
        }
        val newExerciseDurationInMinutes =
                exercise.durationOfExecution.toMinutes() + exercise.durationOfRest.toMinutes()
        return currentDurationInMinutes + newExerciseDurationInMinutes > maximumWorkoutDuration.toMinutes()
    }

}
