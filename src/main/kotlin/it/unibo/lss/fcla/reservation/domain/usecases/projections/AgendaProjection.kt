package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda

class AgendaProjection(val init: Agenda) {

    constructor() : this(Agenda())

    fun update(event: Event): Agenda {
        // TODO switch(event)
        return init
    }
}