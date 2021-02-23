package it.unibo.lss.fcla.consultingContext.events

import it.unibo.lss.fcla.consultingContext.interfaces.DomainEvent
import it.unibo.lss.fcla.consultingContext.interfaces.EventStore

/**
 * @author Stefano Braggion
 *
 * In memory implementation of an event store
 */
class InMemoryEventStore : EventStore {

    /**
     *
     */
    override fun save(event: DomainEvent) {
        TODO("Not yet implemented")
    }

    override fun saveAll(events: List<DomainEvent>) {
        TODO("Not yet implemented")
    }

    override fun readAll(): List<DomainEvent> {
        TODO("Not yet implemented")
    }
}