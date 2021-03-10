package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation

/**
 * An Immutable Entity that is used to store into the [consultingReservationList]
 * all the consulting reservations present into the system.
 */
class MemberConsultingReservation(val consultingReservationList: List<ConsultingReservation>) {

    constructor() : this(listOf<ConsultingReservation>())

    /**
     * This method is used to add a [consultingReservation] into the list of all member reservations
     */
    fun addConsultingReservation(consultingReservation: ConsultingReservation): MemberConsultingReservation {
        return MemberConsultingReservation(
            consultingReservationList + consultingReservation
        )
    }

    /**
     * This method is used to remove a [consultingReservation] from the list of all member reservations
     */
    fun deleteConsultingReservation(consultingReservation: ConsultingReservation): MemberConsultingReservation {
        return MemberConsultingReservation(
            consultingReservationList - consultingReservation
        )
    }
}
