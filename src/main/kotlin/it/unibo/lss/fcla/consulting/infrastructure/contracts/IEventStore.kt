package it.unibo.lss.fcla.consulting.infrastructure.contracts

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *
 */
interface IEventStore {

    fun save(domainEvent: DomainEvent)

    fun saveAll(domainEvents: List<DomainEvent>)

    fun readAll(): List<DomainEvent>
}
