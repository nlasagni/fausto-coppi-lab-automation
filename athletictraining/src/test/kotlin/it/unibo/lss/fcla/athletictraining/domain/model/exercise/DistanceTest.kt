package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.DistanceDoesNotRespectRange
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

/**
 * Tests of the [Distance] Value Object.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class DistanceTest : FreeSpec({
    "The distance of an exercise should" - {
        "not allow outside-range values" - {
            assertThrows<DistanceDoesNotRespectRange> {
                Distance(Distance.ZERO - 1)
            }
        }
        "allow the sum of another distance value" - {
            val firstDistance = Distance()
            val secondDistance = Distance()
            val distanceSum = firstDistance + secondDistance
            val expectedDistanceSum = Distance(Distance.ZERO + Distance.ZERO)
            Assertions.assertEquals(expectedDistanceSum, distanceSum)
        }
        "allow the subtraction of another distance value" - {
            val firstDistance = Distance(Distance.ZERO)
            val secondDistance = Distance(Distance.ZERO)
            val distanceSubtraction = firstDistance - secondDistance
            val expectedDistanceSubtraction = Distance(Distance.ZERO - Distance.ZERO)
            Assertions.assertEquals(expectedDistanceSubtraction, distanceSubtraction)
        }
    }
})
