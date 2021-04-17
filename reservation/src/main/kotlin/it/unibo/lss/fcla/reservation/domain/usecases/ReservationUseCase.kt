/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.domain.usecases.projections.Projection
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Abstract class for reservation use case
 */
abstract class ReservationUseCase {

    protected abstract val eventStore: EventStore

    /**
     * Return an aggregate [T] given its [aggregateId] and the related [projection] based on the
     * events present into the [EventStore]
     */
    protected fun <T> computeAggregate(aggregateId: UUID, projection: Projection<T>): T {
        return eventStore.getStream(aggregateId)
            .fold(projection.init) { state, event -> projection.update(state, event) }
    }
}
