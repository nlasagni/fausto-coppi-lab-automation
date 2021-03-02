package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.consulting.Date
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerLastNameCannotBeNull
import it.unibo.lss.fcla.consulting.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.freelancer.FreelancerRole
import java.time.LocalTime

class FreelancerTest : FreeSpec({

    "creation freelancer with empty first name should throw exception" - {
        shouldThrow<FreelancerFirstNameCannotBeNull> {
            Freelancer(
                freelancerId = FreelancerId("F-12345"),
                firstName = "",
                lastName = "turing",
                FreelancerRole.AthleticTrainer()
            )
        }
    }

    "creation freelancer with empty last name should throw exception" - {
        shouldThrow<FreelancerLastNameCannotBeNull> {
            Freelancer(
                freelancerId = FreelancerId("F-12345"),
                firstName = "alan",
                lastName = "",
                FreelancerRole.AthleticTrainer()
            )
        }
    }

    "test freelancer role inequality" - {
        val physiotherapist = FreelancerRole.Physiotherapist()
        val nutritionist = FreelancerRole.Nutritionist()

        assert(!physiotherapist.equals(nutritionist))
    }

    "test freelancer role equalty" - {
        val firstNutritionist = FreelancerRole.Nutritionist()
        val secondNutritionist = FreelancerRole.Nutritionist()

        assert(firstNutritionist == secondNutritionist)
    }

    "test no duplicate availability on the same date for freelancer" - {
        val freelancer = Freelancer(
            freelancerId = FreelancerId("F-12345"),
            firstName = "Mario",
            lastName = "Rossi",
            role = FreelancerRole.Biomechanical()
        )
        val date = Date(2021, 1, 1)

        freelancer.addAvailability(newAvailabilityDate = date, fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        shouldThrow<ConsultingException> {
            freelancer.addAvailability(newAvailabilityDate = date, fromTime = LocalTime.MIN, toTime = LocalTime.MAX)
        }
    }
})
