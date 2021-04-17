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
 * Thrown to indicate that the provided workout identifier
 * is not valid.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class WorkoutNotFound :
    UseCaseException("Workout with provided identifier not found.")
