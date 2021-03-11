package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import org.junit.jupiter.api.Assertions

/**
 * Test of the [Purpose] Value Object.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class PurposeTest : FreeSpec({
    "Strengthening should" - {
        "be equal to another with same name" - {
            val firstStrengthening = Purpose.InjuryRecovery()
            val secondStrengthening = Purpose.InjuryRecovery()
            Assertions.assertEquals(firstStrengthening, secondStrengthening)
        }
        "be different from another with different name" - {
            val strengthening = Purpose.InjuryRecovery()
            val injuryRecovery = Purpose.InjuryRecovery("Strengthening")
            Assertions.assertNotEquals(strengthening, injuryRecovery)
        }
    }
    "Injury Recovery should" - {
        "be equal to another with same name" - {
            val firstInjuryRecovery = Purpose.InjuryRecovery()
            val secondInjuryRecovery = Purpose.InjuryRecovery()
            Assertions.assertEquals(firstInjuryRecovery, secondInjuryRecovery)
        }
        "be different from another with different name" - {
            val injuryRecovery = Purpose.InjuryRecovery()
            val strengthening = Purpose.Strengthening("Injury Recovery")
            Assertions.assertNotEquals(injuryRecovery, strengthening)
        }
    }
    "Discharge should" - {
        "be equal to another with same name" - {
            val firstDischarge = Purpose.Discharge()
            val secondDischarge = Purpose.Discharge()
            val strengthening = Purpose.Strengthening("Discharge")
            Assertions.assertEquals(firstDischarge, secondDischarge)
            Assertions.assertNotEquals(firstDischarge, strengthening)
        }
        "be different from another with different name" - {
            val discharge = Purpose.Discharge()
            val strengthening = Purpose.Strengthening("Discharge")
            Assertions.assertNotEquals(discharge, strengthening)
        }
    }
    "Recovery should" - {
        "be equal to another with same name" - {
            val firstRecovery = Purpose.Recovery()
            val secondRecovery = Purpose.Recovery()
            val strengthening = Purpose.Strengthening("Recovery")
            Assertions.assertEquals(firstRecovery, secondRecovery)
            Assertions.assertNotEquals(firstRecovery, strengthening)
        }
        "be different from another with different name" - {
            val recovery = Purpose.Recovery()
            val strengthening = Purpose.Strengthening("Recovery")
            Assertions.assertNotEquals(recovery, strengthening)
        }
    }
})
