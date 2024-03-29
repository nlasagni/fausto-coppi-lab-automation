/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * An aggregate root used to identify a specific member giving the [firstName], [lastName] and [id].
 *
 * Throws [MemberDataMustNotBeEmpty] if a member is created with empty data
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

    override fun toString(): String {
        return "Member $firstName, $lastName, $id"
    }
}
