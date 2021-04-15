/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.application.interfaces

import it.unibo.lss.fcla.reservation.presentation.Request
import it.unibo.lss.fcla.reservation.presentation.Response

/**
 * Interface to handle the conversion logic from and to string
 */
interface Presenter {

    /**
     * convert a [request] string into the corresponding [Request]
     */
    fun convertRequest(request: String): Request

    /**
     * convert a [Response] into the corresponding [String]
     */
    fun convertResponse(response: Response): String
}
