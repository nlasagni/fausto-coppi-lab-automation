package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.NameMustNotBeEmpty
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Nicola Lasagni on 25/02/2021.
 */
class Workout(private val trainingPlanId: String,
              private val name: String,
              private val day: LocalDate,
              private val time: LocalTime) {

    private val id: String
    private var exercises: List<Exercise> = emptyList()

    init {
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        id = generateId()
    }

    private fun generateId() = "$trainingPlanId-$name-day-$time"

    fun snapshot(): Workout.Snapshot = Workout.Snapshot(name, day, time, exercises)

    fun prepareExercise(exercise: Exercise) {
        exercises = exercises + exercise
    }

    class Snapshot(val name: String,
                   val day: LocalDate,
                   val time: LocalTime,
                   val exercises: List<Exercise>)

}