package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import java.util.Date
import java.util.UUID

/**
 * Facade class used to have access to all consulting reservation information.
 *
 * The information are: the [date] of the reservation, the [freelancerId] which is related
 * to the consulting reservation, the [id] of the facade and [isOpen] which is used to know
 * if a reservation is open or close.
 */
data class ConsultingReservationFacade(
    override val date: Date,
    override val freelancerId: UUID,
    override val id: UUID,
    val isOpen: Boolean
) : ConsultingReservation {

    constructor(openConsultingReservation: OpenConsultingReservation) : this(
        openConsultingReservation.date,
        openConsultingReservation.freelancerId,
        openConsultingReservation.id,
        isOpen = true
    )

    constructor(closeConsultingReservation: CloseConsultingReservation) : this(
        closeConsultingReservation.date,
        closeConsultingReservation.freelancerId,
        closeConsultingReservation.id,
        isOpen = false
    )
}