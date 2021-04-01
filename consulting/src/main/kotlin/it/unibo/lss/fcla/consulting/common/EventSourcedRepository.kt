package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.application.adapters.EventStore
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * Represent an event sourced repository which store all
 * changes to the event store.
 *
 */
abstract class EventSourcedRepository<A : AbstractAggregate>(eventStore: EventStore) : IRepository<A> {

    private val store = eventStore

    /**
     * Retrieve from the event store all the events related to an aggregate
     * identified by an [aggregateId]
     *
     */
    override fun getById(aggregateId: AggregateId): List<DomainEvent> {
        return store.getEventsForAggregate(aggregateId)
    }

    /**
     * Get all the uncommitted events from the aggregate and store each of them
     * to the event store.
     * Clear all saved events from the aggregate
     */
    override fun save(aggregate: A) {
        val uncommittedEvents = aggregate.getUncommittedEvents()
        uncommittedEvents.forEach { store.saveEvent(aggregateId = aggregate.aggregateId, it) }

        aggregate.clearUncommittedEvents()
    }

    /**
     * Retrieve the map with all [DomainEvent] of each [AggregateId]
     */
    override fun getAllEvents(): HashMap<AggregateId, List<DomainEvent>> {
        return store.getAllEvents()
    }
}
