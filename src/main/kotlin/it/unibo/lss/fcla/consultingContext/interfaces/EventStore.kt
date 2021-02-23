package it.unibo.lss.fcla.consultingContext.interfaces

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