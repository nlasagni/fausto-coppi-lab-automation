package it.unibo.lss.fcla.consulting.domain.contracts

import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.common.EventDescriptor

/**
 * @author Stefano Braggion
 *
 */
interface IEventStore {

    fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent)
    fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent>
    fun getAllEvents() : HashMap<AggregateId, List<DomainEvent>> //TODO replace with EventDescriptor
}
