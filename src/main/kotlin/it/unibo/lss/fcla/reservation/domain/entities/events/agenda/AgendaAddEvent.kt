package it.unibo.lss.fcla.reservation.domain.entities.events.agenda

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

class AgendaAddEvent(override val id: UUID) : Event
