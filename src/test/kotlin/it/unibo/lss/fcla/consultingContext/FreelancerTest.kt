package it.unibo.lss.fcla.consultingContext

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consultingContext.freelancer.Freelancer
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerId
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerRole
import java.time.LocalTime

class FreelancerTest : FreeSpec({

    "creation freelancer with empty first name should throw exception" - {
        shouldThrow<IllegalArgumentException> {
            Freelancer(
                freelancerId = FreelancerId("F12345"),
                firstName = "",
                lastName = "turing",
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

        assert(firstNutritionist.equals(secondNutritionist))
    }

    "test no duplicate availability on the same date for freelancer" - {
        val freelancer = Freelancer(
            freelancerId = FreelancerId("F12345"),
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
