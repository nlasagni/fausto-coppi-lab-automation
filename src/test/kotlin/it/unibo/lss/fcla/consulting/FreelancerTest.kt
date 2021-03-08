package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityDoesNotExist
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerLastNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import java.time.LocalTime

class FreelancerTest : FreeSpec({

    "A freelancer should not be created without a firstname " - {
        shouldThrow<FreelancerFirstNameCannotBeNull> {
            Freelancer.createFreelancer(
                freelancerId = "12345",
                firstName = "",
                lastName = "turing",
                FreelancerRole.AthleticTrainer()
            )
        }
    }

    "A freelancer should not be created without a lastname" - {
        shouldThrow<FreelancerLastNameCannotBeNull> {
            Freelancer.createFreelancer(
                freelancerId = "12345",
                firstName = "alan",
                lastName = "",
                FreelancerRole.AthleticTrainer()
            )
        }
    }

    "Test freelancer role inequality" - {
        val physiotherapist = FreelancerRole.Physiotherapist()
        val nutritionist = FreelancerRole.Nutritionist()

        assert(!physiotherapist.equals(nutritionist))
    }

    "Test freelancer role equality" - {
        val firstNutritionist = FreelancerRole.Nutritionist()
        val secondNutritionist = FreelancerRole.Nutritionist()

        assert(firstNutritionist == secondNutritionist)
    }

    "A freelancer cannot have more than one availability per day" - {
        val freelancer = Freelancer.createFreelancer(
            freelancerId = "12345",
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

    "Test freelancer correctly update the availability for the day" - {
        val freelancer = Freelancer.createFreelancer(
            freelancerId = "12345",
            firstName = "Mario",
            lastName = "Rossi",
            role = FreelancerRole.Biomechanical()
        )

        val date = Date(2021, 1, 1)
        freelancer.addAvailability(newAvailabilityDate = date, fromTime = LocalTime.MIN, toTime = LocalTime.MAX)
        freelancer.updateAvailability(availabilityDate = date, fromTime = LocalTime.MIDNIGHT, toTime = LocalTime.MAX)

        assert(freelancer.availabilityOfDay(date)?.fromTime == LocalTime.MIDNIGHT &&
            freelancer.availabilityOfDay(date)?.toTime == LocalTime.MAX)
    }

    "Updating the availability for a day that not exist should throw error" - {
        val freelancer = Freelancer.createFreelancer(
            freelancerId = "12345",
            firstName = "Mario",
            lastName = "Rossi",
            role = FreelancerRole.Biomechanical()
        )

        val date = Date(2021, 1, 1)

        shouldThrow<FreelancerAvailabilityDoesNotExist> {
            freelancer.updateAvailability(date, LocalTime.MIN, LocalTime.MAX)
        }
    }
})
