package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.consulting.createPhysiotherapyConsulting
import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import it.unibo.lss.fcla.consulting.domain.freelancer.createBiomechanical
import java.time.LocalTime

class EventTests : FreeSpec ({

    "Freelancer event store and rehydrating test" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val aggregateID = "123"
        val date = Date(year = 2021, month = 1, day = 1)
        val expectedEvents = 6

        val freelancer = Freelancer.createBiomechanical(aggregateID, firstName = "Alan",
            lastName = "Turing")

        freelancer.addAvailability(newAvailabilityDate = date,
        fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        freelancer.updateAvailability(availabilityDate = date, fromTime = LocalTime.MIDNIGHT, toTime = LocalTime.MAX)
        freelancer.deleteAvailability(availabilityDate = date)
        freelancer.addAvailability(newAvailabilityDate = date, fromTime = LocalTime.MIN, toTime = LocalTime.MAX)

        aggregateRepository.save(freelancer)

        assert(eventStore.getEventsForAggregate(aggregateID).count() == expectedEvents)
        assert(freelancer.getUncommittedEvents().count() == 0)

        val rehydratedAggregate = Freelancer.rehydrateFreelancer(aggregateID, eventStore.getEventsForAggregate(aggregateID))
        assert(rehydratedAggregate.getAvailabilityForDay(date) ==
                AvailabilityHours(fromTime = LocalTime.MIN, toTime = LocalTime.MAX))
    }

    "Consulting event store and rehydrating test" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        val aggregateId = "C001"
        val date = Date(year = 2021, month = 1, day = 1)
        val expectedEvents = 3

        val consulting = Consulting.createPhysiotherapyConsulting(aggregateId, memberId = "M001",
        consultingDate = date, freelancerId = "F001", description = "first description")

        consulting.updateSummaryDescription("second description")
        consulting.updateSummaryDescription("third description")

        aggregateRepository.save(consulting)

        assert(eventStore.getEventsForAggregate(aggregateId).count() == expectedEvents)
        assert(consulting.getUncommittedEvents().count() == 0)

        val rehydratedAggregate = Consulting.rehydrateConsulting(aggregateId, memberId = "M001",
            eventStore.getEventsForAggregate(aggregateId))
        assert(rehydratedAggregate.getSummaryDescription() == "third description")
    }

    "Consulting event store and rehydrating with multiple aggregates" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        val firstAggregateId = "C001"
        val secondAggregateId = "C002"
        val thirdAggregateId = "C003"
        val date = Date(year = 2021, month = 1, day = 1)

        val firstConsulting = Consulting.createPhysiotherapyConsulting(firstAggregateId, memberId = "M001",
            consultingDate = date, freelancerId = "F001", description = "first description")

        val secondConsulting = Consulting.createPhysiotherapyConsulting(secondAggregateId, memberId = "M001",
            consultingDate = date, freelancerId = "F001", description = "first description")

        val thirdConsulting = Consulting.createPhysiotherapyConsulting(thirdAggregateId, memberId = "M002",
            consultingDate = date, freelancerId = "F001", description = "first description")

        firstConsulting.updateSummaryDescription("updated description of first consulting")
        secondConsulting.updateSummaryDescription("updated description of second consulting")
        thirdConsulting.updateSummaryDescription("updated description of third consulting")

        aggregateRepository.save(firstConsulting)
        aggregateRepository.save(secondConsulting)
        aggregateRepository.save(thirdConsulting)

    }
})