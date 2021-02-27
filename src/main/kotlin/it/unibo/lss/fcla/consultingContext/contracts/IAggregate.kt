package it.unibo.lss.fcla.consultingContext.contracts

/**
 * @author Stefano Braggion
 *
 * Represents an interface that each aggregate must implement
 */
interface IAggregate {

    fun applyEvent(event: DomainEvent)
}
