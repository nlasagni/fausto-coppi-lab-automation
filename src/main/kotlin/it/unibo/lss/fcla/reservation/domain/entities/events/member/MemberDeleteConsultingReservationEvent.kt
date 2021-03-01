package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * [Event] representing a deleted consulting reservation
 */
data class MemberDeleteConsultingReservationEvent(
    override val id: UUID,
    val reservation: ConsultingReservation
) : Event
