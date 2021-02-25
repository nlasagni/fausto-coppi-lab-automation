package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.WorkoutReservation

class MemberRequestedWorkoutReservation {
    private var workoutReservationList: MutableList<WorkoutReservation> = mutableListOf()

    /**
     * This method is used to add a [workoutReservation] into the list of all member reservations
     */
    fun addWorkoutReservation(workoutReservation: WorkoutReservation) {
        this.workoutReservationList.add(workoutReservation)
    }

    /**
     * This method is used to remove a [workoutReservation] from the list of all member reservations
     */
    fun deleteWorkoutReservation(workoutReservation: WorkoutReservation) {
        this.workoutReservationList.remove(workoutReservation)
    }

    /**
     * This method is used to return the list of [WorkoutReservation] of this member
     */
    fun getAllMemberWorkout(): List<WorkoutReservation> {
        return workoutReservationList.toList()
    }
}
