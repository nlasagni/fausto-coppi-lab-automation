package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

data class ConsultingReservationUpdateDateEvent(
    override val id: UUID,
    val date: Date
) : Event
