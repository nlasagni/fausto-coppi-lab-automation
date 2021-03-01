package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * [Event] representing an update to a consulting reservation date
 */
data class ConsultingReservationUpdateDateEvent(
    override val id: UUID,
    val date: Date
) : Event
