package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import it.unibo.lss.fcla.reservation.domain.usecases.projections.Projection
import java.util.UUID

abstract class ReservationUseCase {

    protected abstract val eventStore: EventStore

    protected fun <T>computeAggregate(aggregateId: UUID, projection: Projection<T>): T {
        return eventStore.getStream(aggregateId).fold(projection.init){state,event->projection.update(state,event)}
    }
}