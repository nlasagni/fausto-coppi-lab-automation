/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.controller

/**
 * The input port of the controller component.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface ControllerInput {
    /**
     * Executes the specified [request] by mean of related use cases.
     */
    fun executeRequest(request: ControllerRequest)
}
