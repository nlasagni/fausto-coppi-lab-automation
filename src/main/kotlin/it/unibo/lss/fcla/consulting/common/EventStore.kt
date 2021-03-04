package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.contracts.IEventStore

class EventStore : IEventStore {
    override fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent, version: Int) {
        TODO("Not yet implemented")
    }

    override fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent> {
        TODO("Not yet implemented")
    }
}