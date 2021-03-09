package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityAlreadyExist
import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.usecases.FreelancerShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases
import java.time.LocalTime

class UseCasesFreelancerTest : FreeSpec ({
    "Cannot create two freelancers with the same Id" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val useCasesFreelancer = FreelancerUseCases(aggregateRepository)

        useCasesFreelancer.createAthleticTrainer(freelancerId = "F001", firstName = "Alan", lastName = "Turing")
        shouldThrow<FreelancerShouldHaveAUniqueId> {
            useCasesFreelancer.createBiomechanical(freelancerId = "F001", firstName = "Mario", lastName = "Rossi")
        }
    }

    "Test add day availabilities for freelancer" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val useCasesFreelancer = FreelancerUseCases(aggregateRepository)
        val id = "F001"
        val date = Date(2021, 1, 1)

        useCasesFreelancer.createAthleticTrainer(freelancerId = id, firstName = "Alan", lastName = "Turing")
        useCasesFreelancer.createFreelancerAvailabilityForDay(freelancerId = id, day = date ,
        fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        shouldThrow<FreelancerAvailabilityAlreadyExist> {
            useCasesFreelancer.createFreelancerAvailabilityForDay(freelancerId = id, day = date,
                fromTime = LocalTime.MIN, toTime = LocalTime.MAX)
        }

        assert(useCasesFreelancer.getFreelancerAvailabilityForDay(freelancerId = id, day = date) ==
                AvailabilityHours(LocalTime.MIN, LocalTime.MAX))
    }
})
