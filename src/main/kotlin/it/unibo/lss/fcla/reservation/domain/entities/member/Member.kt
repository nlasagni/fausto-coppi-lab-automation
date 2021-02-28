package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty
import java.util.UUID

/**
 * Class used to identify a member giving the [firstName], [lastName] and [id]
 */
class Member(
    val firstName: String,
    val lastName: String,
    val id: UUID,
    private val memberWorkoutReservation: MemberWorkoutReservation,
    private val memberConsultingReservation: MemberConsultingReservation
) {

    constructor(firstName: String, lastName: String, id: UUID) : this(
        firstName,
        lastName,
        id,
        MemberWorkoutReservation(),
        MemberConsultingReservation()
    )

    init {
        if (lastName.isEmpty()) throw MemberDataMustNotBeEmpty()
        if (firstName.isEmpty()) throw MemberDataMustNotBeEmpty()
    }

    /**
     * Returns a [Member] adding a [workoutReservation] into the list of member requested consulting
     */
    fun addWorkoutReservation(workoutReservation: WorkoutReservation): Member {
        return Member(
            firstName,
            lastName,
            id,
            memberWorkoutReservation.addWorkoutReservation(workoutReservation),
            memberConsultingReservation
        )
    }

    /**
     * Returns a [Member] adding a [consultingReservation] into the list of member requested consulting
     */
    fun addConsultingReservation(consultingReservation: ConsultingReservation): Member {
        return Member(
            firstName,
            lastName,
            id,
            memberWorkoutReservation,
            memberConsultingReservation.addConsultingReservation(consultingReservation)
        )
    }

    /**
     * Returns a new [Member] deleting a [workoutReservation] from the list of memberRequestedConsulting list
     */
    fun deleteWorkoutReservation(workoutReservation: WorkoutReservation): Member {
        return Member(
            firstName,
            lastName,
            id,
            memberWorkoutReservation.deleteWorkoutReservation(workoutReservation),
            memberConsultingReservation
        )
    }

    /**
     * Returns a new [Member] deleting a [consultingReservation] from the list of memberRequestedConsulting list
     */
    fun deleteConsultingReservation(consultingReservation: ConsultingReservation): Member {
        return Member(
            firstName,
            lastName,
            id,
            memberWorkoutReservation,
            memberConsultingReservation.deleteConsultingReservation(consultingReservation)
        )
    }

    /**
     * Returns a list of [ConsultingReservation]
     */
    fun retrieveConsultingReservation(): List<ConsultingReservation> {
        return memberConsultingReservation.consultingReservationList
    }

    /**
     * Returns a list of [WorkoutReservation]
     */
    fun retrieveWorkoutReservation(): List<WorkoutReservation> {
        return memberWorkoutReservation.workoutReservationList
    }
}
