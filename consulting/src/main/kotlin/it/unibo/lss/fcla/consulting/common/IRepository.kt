package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *  Interface for a generic repository
 */
interface IRepository<A : AbstractAggregate> {
    fun getById(aggregateId: AggregateId): List<DomainEvent>
    fun getAllEvents(): HashMap<AggregateId, List<DomainEvent>>
    fun save(aggregate: A)
}
