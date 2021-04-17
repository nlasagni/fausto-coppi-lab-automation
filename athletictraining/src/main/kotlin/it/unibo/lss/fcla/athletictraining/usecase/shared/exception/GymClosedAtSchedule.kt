/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.exception

/**
 * Thrown to indicate that the desired schedule refers to a datetime where the gym is closed.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class GymClosedAtSchedule :
    UseCaseException("The provided schedules refers to a datetime where the gym is closed.")
