package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

data class UpdateConsultingReservationEvent(
    override val id: UUID,
    val reservationId: UUID,
    val freelancer: String,
    val date: Date
) : Event
