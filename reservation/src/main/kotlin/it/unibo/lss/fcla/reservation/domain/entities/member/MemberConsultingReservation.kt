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

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

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
