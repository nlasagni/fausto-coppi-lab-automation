package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.models.Freelancer
import it.unibo.lss.fcla.consulting.models.FreelancerRole

class FreelancerTest : FreeSpec({

    "creation freelancer with empty first name should throw exception" - {
        shouldThrow<IllegalArgumentException> {
            Freelancer(firstName = "", lastName = "turing", FreelancerRole.AthleticTrainer())
        }
    }
})
