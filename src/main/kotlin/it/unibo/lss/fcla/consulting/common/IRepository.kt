package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *
 */
interface IRepository<A: AbstractAggregate> {
    fun getById(aggregateId: AggregateId) : List<DomainEvent>
    fun getAllEvents() : HashMap<AggregateId, List<DomainEvent>> //TODO replace with EventDescriptor
    fun save(aggregate: A)
}

