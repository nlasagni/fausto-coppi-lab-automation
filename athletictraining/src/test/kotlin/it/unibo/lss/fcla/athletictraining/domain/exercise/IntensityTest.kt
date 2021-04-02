package it.unibo.lss.fcla.athletictraining.domain.exercise

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.IntensityDoesNotRespectRange
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
class IntensityTest : FreeSpec({
    "The intensity should" - {
        "not allow outside range values" - {
            assertThrows<IntensityDoesNotRespectRange> {
                Intensity(Intensity.MAX + 1)
            }
        }
        "allow the sum of another Intensity" - {
            val firstIntensity = Intensity()
            val secondIntensity = Intensity()
            val intensitySum = firstIntensity + secondIntensity
            val expectedIntensitySum = Intensity(Intensity.MIN + Intensity.MIN)
            Assertions.assertEquals(intensitySum, expectedIntensitySum)
        }
        "allow the subtraction of another Intensity" - {
            val firstIntensity = Intensity(Intensity.MAX)
            val secondIntensity = Intensity()
            val intensitySubtraction = firstIntensity - secondIntensity
            val expectedIntensitySubtraction = Intensity(Intensity.MAX - Intensity.MIN)
            Assertions.assertEquals(intensitySubtraction, expectedIntensitySubtraction)
        }
    }
})
