/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation

import it.unibo.lss.fcla.reservation.application.Controller

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

class InteractiveReservationMicroserviceStarterClass {
    fun launchReservationMicroservice() {
        Controller().start()
    }
}

fun main() {
    InteractiveReservationMicroserviceStarterClass().launchReservationMicroservice()
}
