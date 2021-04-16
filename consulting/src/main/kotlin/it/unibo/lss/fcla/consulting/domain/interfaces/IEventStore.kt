/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.interfaces

import it.unibo.lss.fcla.consulting.common.AggregateId

/**
 * @author Stefano Braggion
 *
 * Interface for event stores
 */
interface IEventStore {

    fun saveEvent(aggregateId: AggregateId, domainEvent: DomainEvent)
    fun getEventsForAggregate(aggregateId: AggregateId): List<DomainEvent>
    fun getAllEvents(): HashMap<AggregateId, List<DomainEvent>>
}
