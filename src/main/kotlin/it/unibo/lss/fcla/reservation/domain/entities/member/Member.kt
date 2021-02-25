package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty

//TODO modifica doc
/**
 * Class used to identify a member
 *
 * @param firstName The name of the member
 * @param lastName The surname of the member
 * @param id The code of member's card
 */
class Member(val firstName: String, val lastName: String, val id: String) {
    private val memberRequestedWorkoutReservation = MemberRequestedWorkoutReservation()
    private val memberRequestedConsultingReservation = MemberRequestedConsultingReservation()

    init {
        if (lastName.isEmpty()) throw MemberDataMustNotBeEmpty()
        if (firstName.isEmpty()) throw MemberDataMustNotBeEmpty()
        if (id.isEmpty()) throw MemberDataMustNotBeEmpty()
    }

    /**
     * This method will return a list of all member requested workout reservation.
     */
    fun getAllMemberRequestedWorkoutReservation(): List<WorkoutReservation> {
        return memberRequestedWorkoutReservation.getAllMemberWorkout()
    }

    /**
     * This method will return a list of all member requested consulting reservation.
     */
    fun getAllMemberRequestedConsultingReservation(): List<ConsultingReservation> {
        return memberRequestedConsultingReservation.getAllMemberConsulting()
    }

    // metodi per gestire eventi

    private fun addWorkoutReservation() {
        TODO()
    }

    private fun addConsultingReservation() {
        TODO()
    }

    private fun updateWorkoutReservation() {
        TODO()
    }

    private fun updateConsultingReservation() {
        TODO()
    }

    private fun deleteWorkoutReservation() {
        TODO()
    }

    private fun deleteConsultingReservation() {
        TODO()
    }
}
