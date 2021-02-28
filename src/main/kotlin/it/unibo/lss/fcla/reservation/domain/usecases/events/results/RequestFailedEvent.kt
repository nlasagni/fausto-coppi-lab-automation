package it.unibo.lss.fcla.reservation.domain.usecases.events.results

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

data class RequestFailedEvent(override val id: UUID, val requestId: UUID) : Event
