package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.contracts.IEventStore

/**
 * @author Stefano Braggion
 *
 * Represent a base implementation of the store in which are
 * persisted the domain events
 */
class EventStore(
    private val events: HashMap<AggregateId, List<DomainEvent>>
) : IEventStore {

    constructor() : this(hashMapOf())

    /**
     *
     */
    override fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent) {
        events += (aggregateId to (events.getOrDefault(aggregateId, listOf())) + domainEvent)
    }

    /**
     *
     */
    override fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent> {
        return events.getOrDefault(aggregateId, listOf())
    }

    /**
     *
     */
    override fun getAllEvents(): HashMap<AggregateId, List<DomainEvent>> = events
}
