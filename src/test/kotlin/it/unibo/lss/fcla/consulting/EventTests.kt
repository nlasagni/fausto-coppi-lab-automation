package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingSummary
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.member.Member

class EventTests : FreeSpec ({

    "Member event store accumulation test" - {
        var eventStore = EventStore()
        var aggregateRepository = MemberMockRepository(eventStore)
        val aggregateID = "123"
        val consultingID = "C1234"

        val member = Member(aggregateID, firstName = "Alan", lastName = "Turing")
        member.receiveConsulting(consultingID, ConsultingSummary(consultingType = "TYPE",
            description = "DESCRIPTION", consultingDate = Date(2021, 1, 1)))

        aggregateRepository.save(member)

        assert(eventStore.getEventsForAggregate(aggregateID).count() == 2)
    }
})