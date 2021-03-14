package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing a deleted consulting reservation
 */
data class MemberDeleteConsultingReservation(
    override val eventId: UUID,
    val reservation: ConsultingReservation
) : Event
