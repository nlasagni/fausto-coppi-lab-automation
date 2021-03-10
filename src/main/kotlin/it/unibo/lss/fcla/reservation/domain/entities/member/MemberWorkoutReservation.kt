package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.WorkoutReservation

/**
 * An Immutable Entity that is used to store into the [workoutReservationList]
 * all the workout reservations present into the system.
 */
class MemberWorkoutReservation(val workoutReservationList: List<WorkoutReservation>) {

    constructor() : this(listOf<WorkoutReservation>())

    /**
     * This method is used to add a [workoutReservation] into the list of all member reservations
     */
    fun addWorkoutReservation(workoutReservation: WorkoutReservation): MemberWorkoutReservation {
        return MemberWorkoutReservation(workoutReservationList + workoutReservation)
    }

    /**
     * This method is used to remove a [workoutReservation] from the list of all member reservations
     */
    fun deleteWorkoutReservation(workoutReservation: WorkoutReservation): MemberWorkoutReservation {
        return MemberWorkoutReservation(workoutReservationList - workoutReservation)
    }
}
