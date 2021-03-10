package it.unibo.lss.fcla.consulting.domain.contracts

import it.unibo.lss.fcla.consulting.common.AggregateId

/**
 * @author Stefano Braggion
 *
 * Interface for event stores
 */
interface IEventStore {

    fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent)
    fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent>
    fun getAllEvents(): HashMap<AggregateId, List<DomainEvent>>
}