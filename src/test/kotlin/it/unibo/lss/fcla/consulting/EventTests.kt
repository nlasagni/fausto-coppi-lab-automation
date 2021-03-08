package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import java.time.LocalTime

class EventTests : FreeSpec ({

    "Freelancer event store accumulation test" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val aggregateID = "123"
        val date = Date(year = 2021, month = 1, day = 1)
        val expectedEvents = 4

        val freelancer = Freelancer.createFreelancer(aggregateID, firstName = "Alan",
            lastName = "Turing", role = FreelancerRole.Biomechanical())

        freelancer.addAvailability(newAvailabilityDate = date,
        fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        freelancer.updateAvailability(availabilityDate = date, fromTime = LocalTime.MIDNIGHT, toTime = LocalTime.MAX)

        freelancer.deleteAvailability(availabilityDate = date)

        aggregateRepository.save(freelancer)

        assert(eventStore.getEventsForAggregate(aggregateID).count() == expectedEvents &&
            freelancer.getUncommittedEvents().count() == 0)
    }
})