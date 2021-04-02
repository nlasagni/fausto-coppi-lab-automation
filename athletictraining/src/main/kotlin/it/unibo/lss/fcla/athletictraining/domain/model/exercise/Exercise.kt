package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.DurationOfExerciseExecutionMustBeGreaterThanZero
import it.unibo.lss.fcla.athletictraining.domain.exception.DurationOfExerciseRestMustBeGreaterThanZero
import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import java.time.Duration

/**
 * The Exercise that can be prepared for a
 * [it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout].
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class Exercise(
    private val name: String,
    private val configuration: Configuration,
    private val durationOfExecution: Duration,
    private val durationOfRest: Duration
) {

    private val id: ExerciseId

    init {
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        if (isLowerOrEqualToZero(durationOfExecution)) {
            throw DurationOfExerciseExecutionMustBeGreaterThanZero()
        }
        if (isLowerOrEqualToZero(durationOfRest)) {
            throw DurationOfExerciseRestMustBeGreaterThanZero()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of this Exercise which will be stored
     * into the [id] private property.
     */
    private fun generateId(): ExerciseId =
        ExerciseId("$name-${configuration.gymMachineId}-$durationOfExecution-$durationOfRest")

    private fun isLowerOrEqualToZero(duration: Duration): Boolean =
        duration <= Duration.ZERO


}
