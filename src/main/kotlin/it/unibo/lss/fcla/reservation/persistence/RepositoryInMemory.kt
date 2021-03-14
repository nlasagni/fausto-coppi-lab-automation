package it.unibo.lss.fcla.reservation.persistence

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.Repository
import java.util.UUID

/**
 * Class used to access to data stored into data layer.
 *
 * This class implements the interface [Repository] exposed by the use case layer.
 */
class RepositoryInMemory : Repository {
    private var eventsInMemoryMap: Map<UUID, List<Event>> = mapOf()

    /**
     * Method used to store events in memory
     */
    override fun writeEvents(events: Map<UUID, List<Event>>) {
        eventsInMemoryMap = events
    }

    /**
     * Method that returns a map of the stored events which are associated to an aggregate id
     */
    override fun readEvents(): Map<UUID, List<Event>> {
        return eventsInMemoryMap.toMap()
    }
}
