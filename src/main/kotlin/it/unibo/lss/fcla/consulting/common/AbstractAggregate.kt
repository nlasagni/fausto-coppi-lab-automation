package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

typealias AggregateId = String

/**
 * @author Stefano Braggion
 *
 * Represents a super layer for all the aggregates and it is identified by
 * an [aggregateId].
 * Here are included all the operation needed to manage events.
 */
abstract class AbstractAggregate(val aggregateId: AggregateId) {

    private val handlers = (emptyMap<Class<*>?, (Any) -> Unit>()).toMutableMap()
    private val uncommittedEvents : MutableList<DomainEvent> = mutableListOf()

    /**
     * Applies an event
     *
     */
    protected abstract fun applyEvent(event: DomainEvent)

    /**
     * Raise an event
     *
     */
    fun raiseEvent(event: DomainEvent) {
        applyEvent(event)
        uncommittedEvents + event
    }

    /**
     * Retrieve the list of uncommitted event, so an event store
     * can persist them
     */
    fun getUncommittedEvents() : Iterable<DomainEvent> = uncommittedEvents.toList().asIterable()

    /**
     * Clear the list of uncommitted events
     */
    fun clearUncommittedEvents() = uncommittedEvents.clear()

}
