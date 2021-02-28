package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

data class DeleteConsultingReservationEvent(
    override val id: UUID,
    val reservationId: UUID) : Event
