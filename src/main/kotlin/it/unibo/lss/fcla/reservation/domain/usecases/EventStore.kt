package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

class EventStore(private var events: Map<UUID, List<Event>>) {

    constructor() : this(mapOf<UUID, List<Event>>())

    fun get(): List<Event> {
        return events.values.fold(listOf()) { acc, list -> acc + list }
    }

    fun getStream(aggregate: UUID): List<Event> {
        return events.getOrDefault(aggregate, listOf()).toList()
    }

    private fun append(aggregate: UUID, newEvents: List<Event>) {
        events = events + (aggregate to (events.getOrDefault(aggregate, listOf()) + newEvents))
    }

    fun evolve(aggregate: UUID, event: Event, producer: Producer) {
        append(aggregate, producer.produce(event))
    }
}
