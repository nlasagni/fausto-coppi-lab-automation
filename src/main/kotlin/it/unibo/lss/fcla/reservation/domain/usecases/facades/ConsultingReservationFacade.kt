package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import java.util.Date
import java.util.UUID

data class ConsultingReservationFacade(
    override val date: Date,
    override val freelancerId: String,
    override val id: UUID,
    val isOpen: Boolean
) : ConsultingReservation
