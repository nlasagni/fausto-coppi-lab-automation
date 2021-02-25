package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation

class MemberRequestedConsultingReservation {
    private var consultingReservationList: MutableList<ConsultingReservation> = mutableListOf()

    /**
     * This method is used to add a [consultingReservation] into the list of all member reservations
     */
    fun addConsultingReservation(consultingReservation: ConsultingReservation) {
        this.consultingReservationList.add(consultingReservation)
    }

    /**
     * This method is used to remove a [consultingReservation] from the list of all member reservations
     */
    fun deleteWorkoutReservation(consultingReservation: ConsultingReservation) {
        this.consultingReservationList.remove(consultingReservation)
    }

    /**
     * This method is used to return the list of [ConsultingReservation] of this member
     */
    fun getAllMemberConsulting(): List<ConsultingReservation> {
        return consultingReservationList.toList()
    }
}
