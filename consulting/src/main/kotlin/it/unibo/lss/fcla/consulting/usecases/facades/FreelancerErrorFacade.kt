/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases.facades

/**
 * @author Stefano Braggion
 *
 * Represent a freelancer error facade model
 */
class FreelancerErrorFacade internal constructor(
    val message: String
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(error: String): FreelancerErrorFacade {
            return FreelancerErrorFacade(
                message = error
            )
        }
    }
}
