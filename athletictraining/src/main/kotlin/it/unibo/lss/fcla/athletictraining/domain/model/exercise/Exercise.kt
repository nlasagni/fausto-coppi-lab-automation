package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.DurationOfExerciseExecutionMustBeGreaterThanZero
import it.unibo.lss.fcla.athletictraining.domain.exception.DurationOfExerciseRestMustBeGreaterThanZero
import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import java.time.Duration

/**
 * The Exercise that can be prepared for a
 * [it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout].
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class Exercise(
    private var name: String,
    private var configuration: Configuration,
    private var durationOfExecution: Duration,
    private var durationOfRest: Duration
) {

    private val id: ExerciseId

    init {
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        if (isLowerThanOrEqualToZero(durationOfExecution)) {
            throw DurationOfExerciseExecutionMustBeGreaterThanZero()
        }
        if (isLowerThanOrEqualToZero(durationOfRest)) {
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

    private fun isLowerThanOrEqualToZero(duration: Duration): Boolean =
        duration <= Duration.ZERO

    /**
     * Changes the name of this exercise.
     * The [newName] must not be empty, otherwise
     * a [NameMustNotBeEmpty] exception is thrown.
     */
    fun rename(newName: String) {
        if (newName.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
        name = newName
    }

    fun changeGymMachine(gymMachineId: GymMachineId) {
        configuration = configuration.changeGymMachine(gymMachineId)
    }

    /**
     * Increments the [Intensity] of the [configuration] of this exercise by
     * the specified [intensity] parameter.
     */
    fun incrementIntensity(intensity: Intensity) {
        configuration = configuration.incrementIntensity(intensity)
    }

    /**
     * Decrements the [Intensity] of the [configuration] of this exercise by
     * the specified [intensity] parameter.
     */
    fun decrementIntensity(intensity: Intensity) {
        configuration = configuration.decrementIntensity(intensity)
    }

    /**
     * Increments the [Distance] of the [configuration] of this exercise by
     * the specified [distance] parameter.
     */
    fun incrementDistance(distance: Distance) {
        configuration = configuration.incrementDistance(distance)
    }

    /**
     * Decrements the [Distance] of the [configuration] of this exercise by
     * the specified [distance] parameter.
     */
    fun decrementDistance(distance: Distance) {
        configuration = configuration.decrementDistance(distance)
    }

}
