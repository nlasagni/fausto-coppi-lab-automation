/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.interfaces.DomainEvent

typealias AggregateId = String

/**
 * @author Stefano Braggion
 *
 * Represents a super layer for all the aggregates and it is identified by
 * an [aggregateId], which is an abstraction of all aggregate ids.
 *
 * In this class are registered all the event handlers of the concrete class.
 */
abstract class AbstractAggregate(val aggregateId: AggregateId) {

    private val handlers = (emptyMap<Class<*>?, (Any) -> Unit>()).toMutableMap()
    private val uncommittedEvents: MutableList<DomainEvent> = mutableListOf()

    /**
     * Applies an event. This method must be implemented by the concrete aggregate
     */
    protected abstract fun applyEvent(event: DomainEvent)

    /**
     * Raise an [event], which is captured by the concrete handler and add it to
     * the list of [uncommittedEvents]
     */
    fun raiseEvent(event: DomainEvent) {
        applyEvent(event)
        uncommittedEvents.add(event)
    }

    /**
     * Retrieve the list of uncommitted events, so an event store implementation
     * can persist them
     */
    fun getUncommittedEvents(): Iterable<DomainEvent> = uncommittedEvents.toList().asIterable()

    /**
     * Clear the list of uncommitted events
     */
    fun clearUncommittedEvents() = uncommittedEvents.clear()
}
