/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * Domain service that checks if the gym is open at a given [Schedule].
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
interface GymOpenChecker {

    /**
     * Checks if the gym is open at the given [schedule].
     * @return true if the gym is open, false otherwise.
     */
    fun isGymOpenForSchedule(schedule: Schedule): Boolean
}
