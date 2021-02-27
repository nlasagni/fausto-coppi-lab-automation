package it.unibo.lss.fcla.consultingContext.contracts

import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *
 */
interface EventStore {

    fun save(event: DomainEvent)

    fun saveAll(events: List<DomainEvent>)

    fun readAll() : List<DomainEvent>
}