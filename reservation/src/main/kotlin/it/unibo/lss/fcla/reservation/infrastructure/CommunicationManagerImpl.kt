/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.infrastructure

import it.unibo.lss.fcla.reservation.application.interfaces.CommunicationManager

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Class to handle the means of communication.
 *
 * It implements the [CommunicationManager] interface exposed by application layer.
 */
class CommunicationManagerImpl : CommunicationManager {
    private val events: ArrayDeque<String> = ArrayDeque()

    /**
     * Method used to pick the first event request from list and returns a string which represents the request.
     */
    override fun pickRequest(): String? {
        return events.removeFirstOrNull()
    }

    /**
     * Method used to publish into the EventBus the request
     */
    override fun publishResult(result: String) {
        println("$result published into the Bus!")
    }
}
