package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.AggregateId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.contracts.IAggregate

typealias AggregateId = String

/**
 * @author Stefano Braggion
 *
 * Represents a super layer for all the aggregates and it is identified by
 * an [aggregateId].
 * Here are included all the operation needed to manage events.
 */
abstract class AbstractAggregate(val aggregateId: AggregateId) : IAggregate {

    private val handlers = (emptyMap<Class<*>?, (Any) -> Unit>()).toMutableMap()
    private val uncommittedEvents : MutableList<DomainEvent> = mutableListOf()

    protected fun <A: AbstractAggregate> applyAndQueueEvent(event: DomainEvent) : A {
        applyEvent(event)
        uncommittedEvents + event
        return this as A
    }

    /**
     * Applies an event
     *
     */
    protected abstract fun applyEvent(event: DomainEvent)

    /**
     * Raise an event
     *
     */
    fun raiseEvent(event: DomainEvent) = applyEvent(event)

    /**
     * Retrieve the list of uncommitted event, so an event store
     * can persist them
     */
    override fun getUncommittedEvents() : Iterable<DomainEvent> = uncommittedEvents.toList().asIterable()

    /**
     * Clear the list of uncommitted events
     */
    override fun clearUncommittedEvents() = uncommittedEvents.clear()
}
