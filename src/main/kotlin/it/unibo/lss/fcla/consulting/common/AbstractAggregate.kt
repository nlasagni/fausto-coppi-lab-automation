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

    /**
     * Register a new handler for event
     */
    internal inline fun <reified T> register(noinline eventHandler: ((T) -> Unit)?) {
        if (eventHandler == null) throw NullPointerException("Handler must not be null")

        handlers[T::class.java] = { eventHandler(it as T) }
    }

    /**
     * Applies an event
     *
     */
    override fun applyEvent(event: DomainEvent) {
        var eventHandler = handlers[event::class.java]
            ?: throw NullPointerException("Handler must not be null")

        uncommittedEvents + event

        eventHandler(event)
    }

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
