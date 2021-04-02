package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.ConfigurationMustBeRelatedToGymMachine
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId

/**
 * A Value Object representing the configuration of an [Exercise].
 *
 * It's composed by an [Intensity] and a [Distance], and refers to a [GymMachineId].
 *
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Configuration(
    val gymMachineId: GymMachineId,
    val intensity: Intensity,
    val distance: Distance
) {

    init {
        if (gymMachineId.value.isEmpty()) {
            throw ConfigurationMustBeRelatedToGymMachine()
        }
    }

    /**
     * Creates a new [Configuration] with the specified [gymMachineId].
     */
    fun changeGymMachine(gymMachineId: GymMachineId) =
        Configuration(gymMachineId, intensity, distance)

    /**
     * Creates a new [Configuration] with an intensity incremented
     * by the specified [intensityToIncrement] parameter.
     */
    fun incrementIntensity(intensityToIncrement: Intensity) =
        Configuration(gymMachineId, intensity + intensityToIncrement, distance)

    /**
     * Creates a new [Configuration] with an intensity decremented
     * by the specified [intensityToDecrement] parameter.
     */
    fun decrementIntensity(intensityToDecrement: Intensity): Configuration =
        Configuration(gymMachineId, intensity - intensityToDecrement, distance)

    /**
     * Creates a new [Configuration] with a distance incremented
     * by the specified [distanceToIncrement] parameter.
     */
    fun incrementDistance(distanceToIncrement: Distance): Configuration =
        Configuration(gymMachineId, intensity, distance + distanceToIncrement)
    /**
     * Creates a new [Configuration] with a distance decremented
     * by the specified [distanceToDecrement] parameter.
     */
    fun decrementDistance(distanceToDecrement: Distance): Configuration =
        Configuration(gymMachineId, intensity, distance - distanceToDecrement)

}
