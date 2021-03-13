package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

interface Repository {

    fun writeEvents(events: Map<UUID, List<Event>>)

    fun readEvents(): Map<UUID, List<Event>>
}
