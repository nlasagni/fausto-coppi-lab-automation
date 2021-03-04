package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import it.unibo.lss.fcla.reservation.domain.usecases.projections.Projection
import java.util.UUID

abstract class ReservationUseCase {

    protected abstract val eventStore: EventStore
    // Fake Id used to aggregate request event
    private val headquarterId: UUID = UUID.randomUUID()

    protected fun handleRequestResult(event: Event, producer: Producer): String {
        eventStore.evolve(headquarterId, event, producer)
        when (val resultEvent = eventStore.getStream(event.id).first()) {
            is RequestSucceededEvent -> return resultEvent.message
            is RequestFailedEvent -> throw RequestFailedException(resultEvent.message)
            else -> throw RequestFailedException()
        }
    }

    protected fun <T>computeAggregate(aggregateId: UUID, projection: Projection<T>): T {
        return eventStore.getStream(aggregateId).fold(projection.init){state,event->projection.update(state,event)}
    }
}