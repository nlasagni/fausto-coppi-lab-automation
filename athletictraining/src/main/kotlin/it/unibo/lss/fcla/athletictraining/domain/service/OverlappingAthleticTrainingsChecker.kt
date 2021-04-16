/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.shared.Period

/**
 * Domain service that checks if there are overlapping athletic trainings
 * in a given period.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
interface OverlappingAthleticTrainingsChecker {

    /**
     * Checks if there is at least one athletic training that overlaps with the
     * specified [period].
     */
    fun existsOverlappingAthleticTraining(
        athleticTrainings: Collection<ActiveAthleticTraining>,
        period: Period
    ): Boolean
}
