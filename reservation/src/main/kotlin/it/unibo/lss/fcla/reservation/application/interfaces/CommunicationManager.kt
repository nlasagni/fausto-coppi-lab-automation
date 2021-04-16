/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.application.interfaces

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Interface to handle the means of communication.
 */
interface CommunicationManager {

    /**
     * Method used to pick event request from list end return it in the form of a string.
     */
    fun pickRequest(): String?

    /**
     * Method used to publish into the EventBus the result of a request
     */
    fun publishResult(result: String)
}
