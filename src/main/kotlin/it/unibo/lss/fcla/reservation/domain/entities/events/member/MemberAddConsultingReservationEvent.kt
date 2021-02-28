package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

data class MemberAddConsultingReservationEvent(
    override val id: UUID,
    val reservation: ConsultingReservation
) : Event
