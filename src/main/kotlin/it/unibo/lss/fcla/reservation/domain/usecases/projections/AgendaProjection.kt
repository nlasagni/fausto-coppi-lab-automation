package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import java.util.UUID

class AgendaProjection(val init: Agenda) {

    constructor() : this(Agenda(UUID.randomUUID()))

    fun update(event: Event): Agenda {
        // TODO switch(event)
        return init
    }
}
