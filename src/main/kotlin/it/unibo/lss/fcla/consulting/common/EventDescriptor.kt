package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * Class used to describe all the event for an
 * aggregate identified by [AggregateId]
 */
class EventDescriptor(/*aggregateId: AggregateId*/) {

    private var eventList: List<DomainEvent> = emptyList()

    fun addEvents(list: List<DomainEvent>) {
        eventList = list
    }

    fun getEvents() : List<DomainEvent> {
        return eventList
    }
}