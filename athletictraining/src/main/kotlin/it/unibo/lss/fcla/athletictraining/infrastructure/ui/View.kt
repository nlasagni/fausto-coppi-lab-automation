/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerInput

/**
 * The [View] component which is responsible of interact with the user.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface View {

    /**
     * Registers the specified [controller] to this view.
     */
    fun registerController(controller: ControllerInput)

    /**
     * Starts this view. Before calling this method, the [registerController] method
     * must be called.
     */
    fun start()
}
