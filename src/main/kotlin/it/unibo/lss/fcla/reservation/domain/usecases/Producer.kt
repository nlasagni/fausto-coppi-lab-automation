package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

interface Producer {
    fun produce(event: Event): Map<UUID, List<Event>>
}
