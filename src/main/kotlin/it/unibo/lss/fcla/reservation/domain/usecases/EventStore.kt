package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

class EventStore(private var events: List<Event>) {

    // TODO Map intead of List

    constructor() : this(listOf<Event>())

    fun get(): List<Event> {
        return events.toList()
    }

    fun getStream(aggregate: UUID): List<Event> {
        return events.toList()
    }

    private fun append(aggregate: UUID, events: List<Event>): Unit {
        this.events = this.events + events
    }

    fun evolve(aggregate: UUID, event: Event): Unit {
        // TODO producer generates events
    }
}