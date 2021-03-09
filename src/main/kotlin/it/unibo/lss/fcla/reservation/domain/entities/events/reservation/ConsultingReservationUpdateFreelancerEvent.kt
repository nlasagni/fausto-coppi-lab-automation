package it.unibo.lss.fcla.reservation.domain.entities.events.reservation

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * An event representing an update to a consulting reservation freelancer
 */
data class ConsultingReservationUpdateFreelancerEvent(
    override val id: UUID,
    val freelancer: String
) : Event
