package it.unibo.lss.fcla.consulting.infrastructure.contracts

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * TODO substitute Int with EventVersion data class
 */
interface IEventStore {

    fun save(domainEvent: DomainEvent, version: Int)

    fun saveAll(domainEvents: List<DomainEvent>, version: Int)

    fun readAll(): List<DomainEvent>
}
