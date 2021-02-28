package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

data class ConsultingReservationUpdateFreelancerEvent(
    override val id: UUID,
    val freelancer: String
) : Event
