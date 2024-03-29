/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * It is referred to a consulting reservation that cannot be updated anymore
 */
class CloseConsultingReservation(
    override val date: Date,
    private val myFreelancerId: UUID,
    override val id: UUID
) : ConsultingReservation {

    override val freelancerId = FreelancerId(myFreelancerId)

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $myFreelancerId in date $date"

    override fun equals(other: Any?): Boolean { return (other is CloseConsultingReservation) && other.id == this.id }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + freelancerId.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
