package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event

interface Projection<T> {
    val init: T
    fun update(state: T, event: Event): T
}