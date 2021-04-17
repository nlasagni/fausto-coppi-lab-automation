/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingSummaryMustHaveAValidFreelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalDate

/**
 * @author Stefano Braggion
 *
 * Value object representing a consulting summary
 * with a [consultingType] and containing a [description], executed by a
 * freelancer identified with [freelancerId] in a specific [consultingDate]
 */
data class ConsultingSummary(
    val consultingDate: LocalDate,
    val freelancerId: FreelancerId,
    val consultingType: ConsultingType,
    val description: String
) {

    init {
        if (description.isEmpty()) {
            throw ConsultingSummaryDescriptionCannotBeEmpty()
        }
        if (freelancerId.isEmpty()) {
            throw ConsultingSummaryMustHaveAValidFreelancer()
        }
    }

    /**
     * String representation of a consulting summary
     */
    override fun toString(): String {
        return "ConsultingSummary(type=$consultingType, description=$description)"
    }
}
