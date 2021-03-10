package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing an added consulting reservation
 */
data class MemberAddConsultingReservation(
    override val eventId: UUID,
    val reservation: ConsultingReservation
) : Event
