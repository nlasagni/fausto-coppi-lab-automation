/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.presentation.freelancer.responses

import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole

/**
 * @author Stefano Braggion
 *
 * Represent a freelancer response object
 */
class FreelancerResponse(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : IResponse {

    /**
     * String representation of the [FreelancerResponse]
     */
    override fun toString(): String =
        "FreelancerResponse(Id=$freelancerId, Firstname=$firstName, Lastname=$lastName, Role=$role)"
}
