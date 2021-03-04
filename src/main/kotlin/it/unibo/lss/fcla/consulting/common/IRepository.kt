package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *
 */
interface IRepository<A: AbstractAggregate> {
    fun getById(aggregateId: AggregateId) : List<DomainEvent>
    fun save(aggregate: A)
}
