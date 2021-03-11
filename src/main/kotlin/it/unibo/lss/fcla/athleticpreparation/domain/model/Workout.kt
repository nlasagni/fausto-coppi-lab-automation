package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.ExceededMaximumWorkoutDuration
import it.unibo.lss.fcla.athleticpreparation.domain.exception.NameMustNotBeEmpty
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

/**
 * A Workout that is scheduled during a [TrainingPlan].
 *
 * @author Nicola Lasagni on 25/02/2021.
 */
class Workout(
    private val name: String,
    private val day: LocalDate,
    private val time: LocalTime
) {

    companion object Workout {
        const val maximumWorkoutDurationInHours: Long = 4
    }

    private val maximumWorkoutDuration: Duration = Duration.ofHours(maximumWorkoutDurationInHours)

    private val id: WorkoutId
    private var exercises: List<Exercise> = emptyList()

    init {
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of this Workout which will be stored
     * into the [id] private property.
     */
    private fun generateId() = WorkoutId("$name-$day-$time")

    /**
     * Generates an [WorkoutSnapshot] with the information about this Workout.
     */
    fun snapshot(): WorkoutSnapshot = WorkoutSnapshot(name, day, time, exercises)

    /**
     * Prepares an [Exercise] for this Workout.
     * If with this [exercise] the [Workout.maximumWorkoutDurationInHours] is exceeded,
     * an [ExceededMaximumWorkoutDuration] exception is thrown.
     */
    fun prepareExercise(exercise: Exercise) {
        if (exceedMaximumDuration(exercise)) {
            throw ExceededMaximumWorkoutDuration()
        }
        exercises = exercises + exercise
    }

    /**
     * Checks if the [exercise] that is going to be prepared makes this Workout
     * exceed the [Workout.maximumWorkoutDurationInHours].
     */
    private fun exceedMaximumDuration(exercise: Exercise): Boolean {
        val currentDurationInMinutes = exercises.map {
            it.durationOfExecution.toMinutes() + it.durationOfRest.toMinutes()
        }.foldRight(Duration.ZERO.toMinutes()) {
            ex1Duration, ex2Duration ->
            ex1Duration + ex2Duration
        }
        val newExerciseDurationInMinutes =
            exercise.durationOfExecution.toMinutes() + exercise.durationOfRest.toMinutes()
        return currentDurationInMinutes + newExerciseDurationInMinutes > maximumWorkoutDuration.toMinutes()
    }
}
