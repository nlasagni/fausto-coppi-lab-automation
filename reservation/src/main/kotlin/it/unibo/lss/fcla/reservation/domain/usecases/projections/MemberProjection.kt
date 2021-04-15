/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.member.Member

/**
 * Projection used to update the [Member] given its events
 */
class MemberProjection(override val init: Member) : Projection<Member> {

    /**
     * Return an updated [Member] based on the given event.
     */
    override fun update(state: Member, event: Event): Member = when (event) {
        is MemberAddConsultingReservation -> state.addConsultingReservation(event.reservation)
        is MemberAddWorkoutReservation -> state.addWorkoutReservation(event.reservation)
        is MemberDeleteConsultingReservation -> state.deleteConsultingReservation(event.reservation)
        is MemberDeleteWorkoutReservation -> state.deleteWorkoutReservation(event.reservation)
        else -> state
    }
}
