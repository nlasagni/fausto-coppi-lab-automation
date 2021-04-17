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

/**
 * @author Stefano Braggion
 *
 * Represent a freelancer error response
 */
class FreelancerErrorResponse(
    val message: String
) : IResponse {

    /**
     * String representation of the [FreelancerErrorResponse]
     */
    override fun toString(): String =
        "FreelancerError($message)"
}
