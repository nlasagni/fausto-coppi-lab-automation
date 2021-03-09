package it.unibo.lss.fcla.reservation.domain.entities.events.agenda

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing a deleted consulting reservation to the Agenda
 */
data class AgendaDeleteConsultingReservationEvent(
    override val id: UUID,
    val reservation: ConsultingReservation
) : Event
