/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.ConfigurationMustBeRelatedToGymMachine
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId

/**
 * A Value Object representing the configuration of an [Exercise].
 *
 * It's composed by an [Intensity] and a [Distance], and refers to a [GymMachineId].
 *
 * @property gymMachine The gym machine to which this Configuration refers.
 * @property intensity The intensity of an Exercise with this Configuration.
 * @property distance The distance that must be done in an Exercise with this Configuration.
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Configuration(
    val gymMachine: GymMachineId,
    val intensity: Intensity,
    val distance: Distance
) {

    init {
        if (gymMachine.value.isEmpty()) {
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
        Configuration(gymMachine, intensity + intensityToIncrement, distance)

    /**
     * Creates a new [Configuration] with an intensity decremented
     * by the specified [intensityToDecrement] parameter.
     */
    fun decrementIntensity(intensityToDecrement: Intensity): Configuration =
        Configuration(gymMachine, intensity - intensityToDecrement, distance)

    /**
     * Creates a new [Configuration] with a distance incremented
     * by the specified [distanceToIncrement] parameter.
     */
    fun incrementDistance(distanceToIncrement: Distance): Configuration =
        Configuration(gymMachine, intensity, distance + distanceToIncrement)
    /**
     * Creates a new [Configuration] with a distance decremented
     * by the specified [distanceToDecrement] parameter.
     */
    fun decrementDistance(distanceToDecrement: Distance): Configuration =
        Configuration(gymMachine, intensity, distance - distanceToDecrement)
}
