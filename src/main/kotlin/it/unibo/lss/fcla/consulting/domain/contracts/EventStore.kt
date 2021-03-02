package it.unibo.lss.fcla.consulting.domain.contracts

/**
 * @author Stefano Braggion
 *
 *
 */
interface EventStore {

    fun save(domainEvent: DomainEvent)

    fun saveAll(domainEvents: List<DomainEvent>)

    fun readAll(): List<DomainEvent>
}
