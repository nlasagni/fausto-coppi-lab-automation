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
import java.util.UUID

/**
 * An aggregate root that is about referencing all the member present into
 * the system and stored into [members] map.
 */
class MemberLedger(private val members: Map<UUID, Member>, val id: UUID) {

    constructor(id: UUID) : this(mapOf(), id)

    /**
     * Returns a [MemberLedger] adding a new [member] into the list of members
     */
    fun addMemberToLedger(member: Member): MemberLedger {
        return MemberLedger(members + (member.id to member), id)
    }

    /**
     * Returns a list of [Member]
     */
    fun retrieveAllMembers(): List<Member> {
        return members.values.toList()
    }

    /**
     * Returns the [Member] of a [consultingReservation]
     */
    fun retrieveMemberWithConsultingReservation(consultingReservation: ConsultingReservation): Member {
        return members.values
            .first { member -> member.retrieveConsultingReservation().contains(consultingReservation) }
    }

    /**
     * Returns the [Member] of a [workoutReservation]
     */
    fun retrieveMemberWithWorkoutReservation(workoutReservation: WorkoutReservation): Member {
        return members.values
            .first { member -> member.retrieveWorkoutReservation().contains(workoutReservation) }
    }
}
