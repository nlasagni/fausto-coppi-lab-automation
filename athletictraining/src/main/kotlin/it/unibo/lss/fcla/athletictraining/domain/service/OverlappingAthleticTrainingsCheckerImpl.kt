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
 * Concrete implementation of an [OverlappingAthleticTrainingsChecker] service.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class OverlappingAthleticTrainingsCheckerImpl : OverlappingAthleticTrainingsChecker {
    override fun existsOverlappingAthleticTraining(
        athleticTrainings: Collection<ActiveAthleticTraining>,
        period: Period
    ): Boolean {
        return athleticTrainings.any { it.overlapsWithPeriod(period) }
    }
}
