/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat5

import it.unibo.lss.fcla.athletictraining.usecase.shared.input.UseCaseInput

/**
 * The [UseCaseInput] port of the [Fclat5RescheduleWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface RescheduleWorkoutInput : UseCaseInput<RescheduleWorkoutRequest>
