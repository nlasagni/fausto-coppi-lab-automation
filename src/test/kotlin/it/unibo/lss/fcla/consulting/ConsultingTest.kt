package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.models.Freelancer
import it.unibo.lss.fcla.consulting.models.FreelancerRole

class ConsultingTest : FreeSpec(
    {

        "test freelancer role inequality" - {
            val physiotherapist = FreelancerRole.Physiotherapist()
            val nutritionist = FreelancerRole.Nutritionist()

            assert(!physiotherapist.equals(nutritionist))
        }

        "test freelancer role equality" - {
            val firstNutritionist = FreelancerRole.Nutritionist()
            val secondNutritionist = FreelancerRole.Nutritionist()

            assert(firstNutritionist.equals(secondNutritionist))
        }

        "test freelancer bad creation throw exception" - {
            shouldThrow<IllegalArgumentException> {
                Freelancer(firstName = "mario", lastName = "", FreelancerRole.AthleticTrainer())
            }
        }
    }
)
