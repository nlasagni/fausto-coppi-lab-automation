/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.adapters

import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.interfaces.DomainEvent
import it.unibo.lss.fcla.consulting.domain.interfaces.IEventStore

/**
 * @author Stefano Braggion
 *
 * Represent a basic and in-memory implementation of the store in which are
 * persisted the domain events
 */
class EventStore(
    private val events: HashMap<AggregateId, List<DomainEvent>>
) : IEventStore {

    constructor() : this(hashMapOf())

    /**
     * Save one [domainEvent] of the specified [aggregateId] in the event store
     */
    override fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent) {
        events += (aggregateId to (events.getOrDefault(aggregateId, listOf())) + domainEvent)
    }

    /**
     * Retrieve the list of all [DomainEvent] of the specified [aggregateId]
     */
    override fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent> {
        return events.getOrDefault(aggregateId, listOf())
    }

    /**
     * Retrieve the map with all [DomainEvent] of each [AggregateId]
     */
    override fun getAllEvents(): HashMap<AggregateId, List<DomainEvent>> = events
}
