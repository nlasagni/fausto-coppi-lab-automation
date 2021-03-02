package it.unibo.lss.fcla.consulting.infrastructure.persistance

import it.unibo.lss.fcla.consulting.domain.contracts.AggregateId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.infrastructure.contracts.IEventStore

/**
 * @author Stefano Braggion
 *
 * In memory implementation of an event store
 */
class InMemoryEventStore : IEventStore {

    private val events = mutableListOf<DomainEvent>()

    /**
     *
     */
    override fun save(id: AggregateId, domainEvent: DomainEvent) {
        TODO("Not yet implemented")
    }

    /**
     *
     */
    override fun saveAll(id: AggregateId, domainEvents: List<DomainEvent>) {
        TODO("Not yet implemented")
    }

    /**
     *
     */
    override fun readAll(id: AggregateId): List<DomainEvent> {
        TODO("Not yet implemented")
    }


}
