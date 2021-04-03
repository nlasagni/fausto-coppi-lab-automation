package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import org.junit.jupiter.api.Assertions

/**
 * Test of the [Purpose] Value Object.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class PurposeTest : FreeSpec({

    "AthleticTraining should" - {
        "be equal to another with same name" - {
            Assertions.assertEquals(Purpose.AthleticPreparation(), Purpose.AthleticPreparation())
        }
        "be different from another with different name" - {
            Assertions.assertNotEquals(Purpose.AthleticPreparation(), Purpose.Strengthening())
        }
    }
    "Strengthening should" - {
        "be equal to another with same name" - {
            Assertions.assertEquals(Purpose.Strengthening(), Purpose.Strengthening())
        }
        "be different from another with different name" - {
            Assertions.assertNotEquals(Purpose.Recovery(), Purpose.InjuryRecovery())
        }
    }
    "Injury Recovery should" - {
        "be equal to another with same name" - {
            Assertions.assertEquals(Purpose.InjuryRecovery(), Purpose.InjuryRecovery())
        }
        "be different from another with different name" - {
            Assertions.assertNotEquals(Purpose.InjuryRecovery(), Purpose.Strengthening())
        }
    }
    "Discharge should" - {
        "be equal to another with same name" - {
            Assertions.assertEquals(Purpose.Discharge(), Purpose.Discharge())
        }
        "be different from another with different name" - {
            Assertions.assertNotEquals(Purpose.Discharge(), Purpose.Strengthening())
        }
    }
    "Recovery should" - {
        "be equal to another with same name" - {
            Assertions.assertEquals(Purpose.Recovery(), Purpose.Recovery())
        }
        "be different from another with different name" - {
            Assertions.assertNotEquals(Purpose.Recovery(), Purpose.Strengthening())
        }
    }
})
