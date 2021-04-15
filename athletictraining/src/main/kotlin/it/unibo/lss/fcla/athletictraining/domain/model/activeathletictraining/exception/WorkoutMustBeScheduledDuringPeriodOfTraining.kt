/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that the desired schedule of a workout is out of the
 * period of the related ActiveAthleticTraining.
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutMustBeScheduledDuringPeriodOfTraining :
    DomainException("A Workout must be scheduled in a day inside the desired period of training.")
