package it.unibo.lss.fcla.reservation.domain.entities.agenda

import it.unibo.lss.fcla.reservation.common.ConsultingReservation

/**
 * An immutable Entity that is about the storage of consulting reservations
 * inside the [consultingReservationList].
 */
class AgendaConsultingReservation(val consultingReservationList: List<ConsultingReservation>) {

    constructor() : this(listOf<ConsultingReservation>())

    /**
     * This method is used to add a [ConsultingReservation] into the list of all member reservations
     */
    fun addConsultingReservation(reservation: ConsultingReservation): AgendaConsultingReservation {
        return AgendaConsultingReservation(consultingReservationList + reservation)
    }

    /**
     * This method is used to remove a [ConsultingReservation] from the list of all member reservations
     */
    fun deleteConsultingReservation(reservation: ConsultingReservation): AgendaConsultingReservation {
        return AgendaConsultingReservation(consultingReservationList - reservation)
    }
}
