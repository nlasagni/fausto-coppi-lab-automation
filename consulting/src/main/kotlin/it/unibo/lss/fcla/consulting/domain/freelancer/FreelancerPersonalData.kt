/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.domain.freelancer.exceptions.FreelancerFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.freelancer.exceptions.FreelancerLastNameCannotBeNull

/**
 * @author Stefano Braggion
 *
 * Value object representing personal data such as [firstName], [lastName]
 * and [role] of a [Freelancer]
 */
data class FreelancerPersonalData(
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) {

    /**
     * Check invariants
     */
    init {
        if (firstName.isEmpty()) {
            throw FreelancerFirstNameCannotBeNull()
        }
        if (lastName.isEmpty()) {
            throw FreelancerLastNameCannotBeNull()
        }
    }
}
