package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty

/**
 * Class used to identify a member giving the [firstName], [lastName] and [id]
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

    /**
     * This method is used to add a [workoutReservation] into the list of member requested workout
     */
    private fun addWorkoutReservation(workoutReservation: WorkoutReservation) {
        memberRequestedWorkoutReservation.addWorkoutReservation(workoutReservation)
    }

    /**
     * This method is used to add a [consultingReservation] into the list of member requested consulting
     */
    private fun addConsultingReservation(consultingReservation: ConsultingReservation) {
        memberRequestedConsultingReservation.addConsultingReservation(consultingReservation)
    }

    /**
     * This method update a workout reservation deleting a [oldWorkoutReservation] and adding a [newWorkoutReservation]
     */
    private fun updateWorkoutReservation(
        oldWorkoutReservation: WorkoutReservation,
        newWorkoutReservation: WorkoutReservation
    ) {
        memberRequestedWorkoutReservation.deleteWorkoutReservation(oldWorkoutReservation)
        memberRequestedWorkoutReservation.addWorkoutReservation(newWorkoutReservation)
    }

    /**
     * This method update a workout reservation deleting a [oldConsultingReservation]
     * and adding a [newConsultingReservation]
     */
    private fun updateConsultingReservation(
        oldConsultingReservation: ConsultingReservation,
        newConsultingReservation: ConsultingReservation
    ) {
        memberRequestedConsultingReservation.deleteConsultingReservation(oldConsultingReservation)
        memberRequestedConsultingReservation.addConsultingReservation(newConsultingReservation)
    }

    /**
     * This method is used to add a [workoutReservation] into the list of member requested workout
     */
    private fun deleteWorkoutReservation(workoutReservation: WorkoutReservation) {
        memberRequestedWorkoutReservation.deleteWorkoutReservation(workoutReservation)
    }

    /**
     * This method is used to remove a [consultingReservation] into the list of member requested consulting
     */
    private fun deleteConsultingReservation(consultingReservation: ConsultingReservation) {
        memberRequestedConsultingReservation.deleteConsultingReservation(consultingReservation)
    }
}
