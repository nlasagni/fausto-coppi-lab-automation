/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.presentation.IRequest

/**
 * @author Stefano Braggion
 *
 * Interface that each controller must implement
 */
interface IController {
    /**
     * Method that take an [IRequest] from the UI and pass it
     * to the use case layer for the execution.
     */
    fun execute(request: IRequest)
}
