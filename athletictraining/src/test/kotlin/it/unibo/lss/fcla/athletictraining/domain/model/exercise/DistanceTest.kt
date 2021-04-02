package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.DistanceDoesNotRespectRange
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
class DistanceTest : FreeSpec({
    "The Distance should" - {
        "not allow outside range values" - {
            assertThrows<DistanceDoesNotRespectRange> {
                Distance(Distance.MIN - 1)
            }
        }
        "allow the sum of another Distance" - {
            val firstDistance = Distance()
            val secondDistance = Distance()
            val distanceSum = firstDistance + secondDistance
            val expectedDistanceSum = Distance(Distance.MIN + Distance.MIN)
            Assertions.assertEquals(distanceSum, expectedDistanceSum)
        }
        "allow the subtraction of another Distance" - {
            val firstDistance = Distance(Distance.MIN)
            val secondDistance = Distance(Distance.MIN)
            val distanceSubtraction = firstDistance - secondDistance
            val expectedDistanceSubtraction = Distance(Distance.MIN - Distance.MIN)
            Assertions.assertEquals(distanceSubtraction, expectedDistanceSubtraction)
        }
    }
})
