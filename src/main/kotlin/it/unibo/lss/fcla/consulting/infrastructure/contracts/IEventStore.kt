package it.unibo.lss.fcla.consulting.infrastructure.contracts

import it.unibo.lss.fcla.consulting.domain.contracts.AggregateId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * TODO substitute Int with EventVersion data class
 */
interface IEventStore {

    fun save(id: AggregateId, domainEvent: DomainEvent, version: Int)

    fun saveAll(id: AggregateId, domainEvents: List<DomainEvent>, version: Int)

    fun readAll(id: AggregateId): List<DomainEvent>
}
