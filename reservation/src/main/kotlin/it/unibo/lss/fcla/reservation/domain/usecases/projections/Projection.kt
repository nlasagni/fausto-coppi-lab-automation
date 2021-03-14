package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event

/**
 * Used to work with information stored.
 *
 * It has an initial state [init] and an [update] function
 */
interface Projection<T> {
    val init: T

    /**
     * Return an updated state [T] given an initial [state] and an [event] to apply to him
     */
    fun update(state: T, event: Event): T
}
