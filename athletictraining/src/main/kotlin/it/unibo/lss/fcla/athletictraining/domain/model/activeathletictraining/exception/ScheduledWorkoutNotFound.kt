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
 * Thrown to indicate that the specified ScheduledWorkout has not been found by the
 * ActiveAthleticTraining Aggregate Root.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ScheduledWorkoutNotFound :
    DomainException("The specified scheduled workout has not been found.")
