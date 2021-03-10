package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * The producer of events which will be stored into the vent Store
 */
interface Producer {

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate.
     */
    fun produce(event: Event): Map<UUID, List<Event>>
}
