package it.unibo.lss.fcla.consulting.contracts

/**
 * @author Stefano Braggion
 *
 * Represents an interface that each aggregate must implement
 */
interface IAggregate {

    fun applyEvent(event: DomainEvent)
}
