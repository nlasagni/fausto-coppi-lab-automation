package it.unibo.lss.fcla.consultingContext.events

import it.unibo.lss.fcla.consultingContext.interfaces.DomainEvent
import it.unibo.lss.fcla.consultingContext.interfaces.EventStore

/**
 * @author Stefano Braggion
 *
 * In memory implementation of an event store
 */
class InMemoryEventStore : EventStore {

    private val events = mutableListOf<DomainEvent>()

    /**
     * Add the given [event] to the event store
     */
    override fun save(domainEvent: DomainEvent) {
        events.add(domainEvent)
    }

    /**
     * Add a list of [events] to the event store
     */
    override fun saveAll(domainEvents: List<DomainEvent>) {
        events.addAll(domainEvents)
    }

    /**
     * Get all the domain events stored in the event store
     */
    override fun readAll(): List<DomainEvent> = events
}