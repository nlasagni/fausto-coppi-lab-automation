package it.unibo.lss.fcla.consulting.infrastructure.contracts

import it.unibo.lss.fcla.consulting.domain.contracts.AggregateId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * 
 */
interface IEventStore {

    fun save(id: AggregateId, domainEvent: DomainEvent)

    fun saveAll(id: AggregateId, domainEvents: List<DomainEvent>)

    fun readAll(id: AggregateId): List<DomainEvent>
}
