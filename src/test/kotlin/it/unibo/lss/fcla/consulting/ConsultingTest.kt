package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec

class ConsultingTest : FreeSpec( {

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
})