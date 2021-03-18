package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.domain.usecases.projections.Projection
import java.util.UUID

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
