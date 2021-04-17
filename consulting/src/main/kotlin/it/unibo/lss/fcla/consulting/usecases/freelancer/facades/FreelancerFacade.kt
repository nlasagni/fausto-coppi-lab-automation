/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases.freelancer.facades

import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import it.unibo.lss.fcla.consulting.usecases.BaseFacade

/**
 * @author Stefano Braggion
 *
 * Represent a [Freelancer] facade model
 */
class FreelancerFacade internal constructor(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(freelancer: Freelancer): FreelancerFacade {

            return FreelancerFacade(
                freelancerId = freelancer.freelancerId,
                firstName = freelancer.getPersonalData().firstName,
                lastName = freelancer.getPersonalData().lastName,
                role = freelancer.getPersonalData().role
            )
        }
    }
}
