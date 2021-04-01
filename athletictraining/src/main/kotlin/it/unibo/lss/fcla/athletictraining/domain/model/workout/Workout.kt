package it.unibo.lss.fcla.athletictraining.domain.model.workout

import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
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
     */
    fun prepareExercise(exercise: Exercise) {
        exercises = exercises + exercise
    }
}
