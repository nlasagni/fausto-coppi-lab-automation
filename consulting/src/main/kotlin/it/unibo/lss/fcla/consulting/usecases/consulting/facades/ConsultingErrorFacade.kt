/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases.consulting.facades

import it.unibo.lss.fcla.consulting.usecases.BaseFacade

/**
 * @author Stefano Braggion
 *
 * Represent a consulting error facade
 */
class ConsultingErrorFacade internal constructor(
    val message: String
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(error: String): ConsultingErrorFacade {
            return ConsultingErrorFacade(
                message = error
            )
        }
    }
}
