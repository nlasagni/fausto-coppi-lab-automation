package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.contracts.IAggregate

/**
 * @author Stefano Braggion
 */
abstract class AbstractAggregate : IAggregate {

    private val handlers = (emptyMap<Class<*>?, (Any) -> Unit>()).toMutableMap()

    /**
     * Register a new handler for event
     */
    internal inline fun <reified T> register(noinline eventHandler: ((T) -> Unit)?) {
        if (eventHandler == null) throw NullPointerException("Handler must not be null")

        handlers[T::class.java] = { eventHandler(it as T) }
    }

    /**
     * Applies an event
     * //TODO add event store
     */
    override fun applyEvent(event: DomainEvent) {
        var eventHandler = handlers[event::class.java]
            ?: throw NullPointerException("Handler must not be null")

        eventHandler(event)
    }

    /**
     * Raise an event
     * //TODO add event store
     */
    fun raiseEvent(event: DomainEvent) {
        applyEvent(event)
    }
}
