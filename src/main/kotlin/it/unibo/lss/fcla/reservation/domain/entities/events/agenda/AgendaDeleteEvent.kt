package it.unibo.lss.fcla.reservation.domain.entities.events.agenda

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

data class AgendaDeleteEvent(override val id: UUID) : Event
