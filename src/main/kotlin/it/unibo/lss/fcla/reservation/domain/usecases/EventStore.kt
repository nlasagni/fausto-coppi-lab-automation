package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * Structure in which store the events tht occurs into the system into the [events] map.
 */
class EventStore(private var events: Map<UUID, List<Event>>) {

    constructor() : this(mapOf<UUID, List<Event>>())

    /**
     * Return a [map] that associates to a specific UUID a list of events
     */
    fun get(): Map<UUID, List<Event>> {
        return events.toMap()
    }

    /**
     * Return all the [List] of events of the [aggregate]
     */
    fun getStream(aggregate: UUID): List<Event> {
        return events.getOrDefault(aggregate, listOf()).toList()
    }

    /**
     * Function that given an [aggregate] and a [newEvents], append them to the map of the event
     * store using the [aggregate] as key and the [newEvents] list as value of that aggregate.
     */
    private fun append(aggregate: UUID, newEvents: List<Event>) {
        events = events + (aggregate to (events.getOrDefault(aggregate, listOf()) + newEvents))
    }

    /**
     * Function that is used to evolve the history of the events occurred into the system and append them to the map
     * associating the [aggregate] to the [event].
     */
    fun evolve(aggregate: UUID, event: Event, producer: Producer) {
        append(aggregate, listOf(event))
        producer.produce(event).forEach { entry -> append(entry.key, entry.value) }
    }
}
