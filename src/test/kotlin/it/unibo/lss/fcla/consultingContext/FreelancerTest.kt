package it.unibo.lss.fcla.consultingContext

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consultingContext.freelancer.Freelancer
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerRole

class FreelancerTest : FreeSpec({

    "creation freelancer with empty first name should throw exception" - {
        shouldThrow<IllegalArgumentException> {
            Freelancer(firstName = "", lastName = "turing", FreelancerRole.AthleticTrainer())
        }
    }
})
