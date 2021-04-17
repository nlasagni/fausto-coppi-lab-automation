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
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.IntensityDoesNotRespectRange
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

/**
 * Tests of the [Intensity] Value Object.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class IntensityTest : FreeSpec({
    "The intensity of an exercise should" - {
        "not allow outside-range values" - {
            assertThrows<IntensityDoesNotRespectRange> {
                Intensity(Intensity.HIGH + 1)
            }
        }
        "allow the sum of another intensity value" - {
            val firstIntensity = Intensity()
            val secondIntensity = Intensity()
            val intensitySum = firstIntensity + secondIntensity
            val expectedIntensitySum = Intensity(Intensity.LOW + Intensity.LOW)
            Assertions.assertEquals(expectedIntensitySum, intensitySum)
        }
        "allow the subtraction of another intensity value" - {
            val firstIntensity = Intensity(Intensity.HIGH)
            val secondIntensity = Intensity()
            val intensitySubtraction = firstIntensity - secondIntensity
            val expectedIntensitySubtraction = Intensity(Intensity.HIGH - Intensity.LOW)
            Assertions.assertEquals(expectedIntensitySubtraction, intensitySubtraction)
        }
    }
})
