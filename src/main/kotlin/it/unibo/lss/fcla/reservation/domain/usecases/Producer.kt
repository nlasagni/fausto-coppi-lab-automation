package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event

interface Producer {
    fun produce(event: Event): List<Event>
}
