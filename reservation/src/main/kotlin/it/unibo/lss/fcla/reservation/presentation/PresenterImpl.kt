/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.presentation

import it.unibo.lss.fcla.reservation.application.interfaces.Presenter

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Implementation of [Request] interface
 *
 * This class handle the conversion logic from and to [String].
 * It is used by application layer to convert messages handled by infrastructure layer.
 *
 */
class PresenterImpl : Presenter {

    /**
     * convert a [String] into the corresponding [Request]
     */
    override fun convertRequest(request: String): Request {
        TODO()
    }

    /**
     * convert a [Response] into the corresponding [String]
     */
    override fun convertResponse(response: Response): String {
        TODO()
    }
}
