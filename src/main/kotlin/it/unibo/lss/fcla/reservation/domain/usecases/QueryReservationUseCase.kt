package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenConsultingReservationProjection
import java.util.UUID

class QueryReservationUseCase(
        private val agendaId: UUID,
        private val ledgerId: UUID,
        private val events: Map<UUID, List<Event>>) : ReservationUseCase() {

    constructor() : this(UUID.randomUUID(),UUID.randomUUID(), mapOf())

    override val eventStore: EventStore = EventStore(events)

    fun retrieveConsultingReservation(reservationId: UUID): ConsultingReservationFacade {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        when (val consultingReservation = agenda.retrieveConsultingReservation()
                .firstOrNull { consultingReservation -> reservationId == consultingReservation.id }) {
            is OpenConsultingReservation -> {
                val openConsultingReservation = computeAggregate(
                        consultingReservation.id,
                        OpenConsultingReservationProjection(consultingReservation))
                return ConsultingReservationFacade(
                        openConsultingReservation.date,
                        openConsultingReservation.freelancerId,
                        openConsultingReservation.id,
                        isOpen = true)
            }
            is CloseConsultingReservation -> {
                return ConsultingReservationFacade(
                        consultingReservation.date,
                        consultingReservation.freelancerId,
                        consultingReservation.id,
                        isOpen = false)
            }
            else -> {
                // TODO Need tests and then remove
                if (consultingReservation != null) {
                    // This should not happen
                    throw RequestFailedException()
                }
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }
}