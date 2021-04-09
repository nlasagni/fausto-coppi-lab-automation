package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.DurationMustBeGreaterThanZero
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.ExerciseIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.NameMustNotBeEmpty

/**
 * The Exercise that can be prepared for a workout.
 *
 * @property id The unique [ExerciseId] of this Exercise.
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class Exercise(
    val id: ExerciseId,
    private var name: String,
    private var configuration: Configuration,
    private var durationOfExecution: Duration,
    private var durationOfRest: Duration
) {

    init {
        if (id.value.isEmpty()) {
            throw ExerciseIdMissing()
        }
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
    }

    /**
     * Generates an [ExerciseSnapshot] with the information about this Exercise.
     */
    fun snapshot() = ExerciseSnapshot(
        id,
        name,
        configuration.gymMachine,
        configuration.intensity,
        configuration.distance,
        durationOfExecution,
        durationOfRest
    )

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

    /**
     * Changes the gym machine to which this exercise refers.
     */
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
