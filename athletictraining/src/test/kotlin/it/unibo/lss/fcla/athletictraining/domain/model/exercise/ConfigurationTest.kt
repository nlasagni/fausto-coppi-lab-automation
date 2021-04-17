/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.ConfigurationMustBeRelatedToGymMachine
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

/**
 * Tests of the [Configuration] Value Object.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class ConfigurationTest : FreeSpec({

    lateinit var gymMachineId: GymMachineId
    lateinit var intensity: Intensity
    lateinit var distance: Distance
    lateinit var validConfiguration: Configuration

    beforeAny {
        gymMachineId = GymMachineId("1234")
        intensity = Intensity(Intensity.NORMAL)
        distance = Distance()
        validConfiguration = Configuration(gymMachineId, intensity, distance)
    }

    "The configuration of an exercise should" - {
        "be related to a gym machine" - {
            assertThrows<ConfigurationMustBeRelatedToGymMachine> {
                Configuration(GymMachineId(""), intensity, distance)
            }
        }
        "allow the incrementation of intensity" - {
            val intensityToIncrement = Intensity()
            val newConfiguration =
                validConfiguration.incrementIntensity(intensityToIncrement)
            val expectedIntensity = intensity + intensityToIncrement
            Assertions.assertEquals(expectedIntensity, newConfiguration.intensity)
        }
        "allow the decrement of intensity" - {
            val intensityToDecrement = Intensity()
            val newConfiguration =
                validConfiguration.decrementIntensity(intensityToDecrement)
            val expectedIntensity = intensity - intensityToDecrement
            Assertions.assertEquals(expectedIntensity, newConfiguration.intensity)
        }
        "allow the increment of distance" - {
            val distanceToIncrement = Distance()
            val newConfiguration =
                validConfiguration.incrementDistance(distanceToIncrement)
            val expectedDistance = distance + distanceToIncrement
            Assertions.assertEquals(expectedDistance, newConfiguration.distance)
        }
        "allow the decrement of distance" - {
            val distanceToDecrement = Distance()
            val newConfiguration =
                validConfiguration.decrementDistance(distanceToDecrement)
            val expectedDistance = distance - distance
            Assertions.assertEquals(expectedDistance, newConfiguration.distance)
        }
    }
})
