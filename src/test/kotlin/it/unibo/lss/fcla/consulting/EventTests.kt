package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import java.time.LocalTime

class EventTests : FreeSpec ({

    "Freelancer event store and rehydrating test" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val aggregateID = "123"
        val date = Date(year = 2021, month = 1, day = 1)
        val expectedEvents = 6

        val freelancer = Freelancer.createFreelancer(aggregateID, firstName = "Alan",
            lastName = "Turing", role = FreelancerRole.Biomechanical())

        freelancer.addAvailability(newAvailabilityDate = date,
        fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        freelancer.updateAvailability(availabilityDate = date, fromTime = LocalTime.MIDNIGHT, toTime = LocalTime.MAX)
        freelancer.deleteAvailability(availabilityDate = date)
        freelancer.addAvailability(newAvailabilityDate = date, fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        aggregateRepository.save(freelancer)

        assert(eventStore.getEventsForAggregate(aggregateID).count() == expectedEvents)
        assert(freelancer.getUncommittedEvents().count() == 0)

        val rehydratedAggregate = Freelancer.rehydrateFreelancer(aggregateID, eventStore.getEventsForAggregate(aggregateID))
        assert(rehydratedAggregate.getAvailabilityFromHours(date) == LocalTime.MIN)
        assert(rehydratedAggregate.getAvailabilityToHours(date) == LocalTime.MAX)
    }

    "Consulting event store and rehydrating test" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        val aggregateId = "C001"
        val date = Date(year = 2021, month = 1, day = 1)
        val expectedEvents = 3

        val consulting = Consulting.createConsulting(aggregateId, memberId = "M001",
        consultingDate = date, freelancerId = "F001", consultingType = ConsultingType.PhysioterapyConsulting(),
        description = "first description")

        consulting.updateSummaryDescription("second description")
        consulting.updateSummaryDescription("third description")

        aggregateRepository.save(consulting)

        assert(eventStore.getEventsForAggregate(aggregateId).count() == expectedEvents)
        assert(consulting.getUncommittedEvents().count() == 0)

        val rehydratedAggregate = Consulting.rehydrateConsulting(aggregateId, eventStore.getEventsForAggregate(aggregateId))
        assert(rehydratedAggregate.getSummaryDescription() == "third description")

    }
})