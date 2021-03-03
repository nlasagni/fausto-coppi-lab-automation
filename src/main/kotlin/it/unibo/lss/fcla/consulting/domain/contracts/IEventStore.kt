package it.unibo.lss.fcla.consulting.domain.contracts

import it.unibo.lss.fcla.consulting.common.AggregateId

/**
 * @author Stefano Braggion
 *
 * TODO substitute Int with EventVersion data class
 */
interface IEventStore {

    fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent, version: Int)
    fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent>
}
