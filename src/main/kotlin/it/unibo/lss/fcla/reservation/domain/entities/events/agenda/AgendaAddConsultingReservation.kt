package it.unibo.lss.fcla.reservation.domain.entities.events.agenda

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing an added consulting reservation to the Agenda
 */
data class AgendaAddConsultingReservation(
    override val eventId: UUID,
    val reservation: ConsultingReservation
) : Event
