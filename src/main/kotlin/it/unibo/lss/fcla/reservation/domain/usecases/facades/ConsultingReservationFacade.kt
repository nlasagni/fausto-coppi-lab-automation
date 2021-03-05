package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import java.util.Date
import java.util.UUID

data class ConsultingReservationFacade(
    override val date: Date,
    override val freelancerId: String,
    override val id: UUID,
    val isOpen: Boolean
) : ConsultingReservation {

    constructor(openConsultingReservation: OpenConsultingReservation) : this(
            openConsultingReservation.date,
            openConsultingReservation.freelancerId,
            openConsultingReservation.id,
            isOpen = true)

    constructor(closeConsultingReservation: CloseConsultingReservation) : this(
            closeConsultingReservation.date,
            closeConsultingReservation.freelancerId,
            closeConsultingReservation.id,
            isOpen = false)
}
