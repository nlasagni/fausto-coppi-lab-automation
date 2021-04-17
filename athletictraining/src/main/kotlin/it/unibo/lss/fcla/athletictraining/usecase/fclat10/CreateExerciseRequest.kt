/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat10

/**
 * Class that represents the request coming from outer layer of
 * creating a new exercise.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class CreateExerciseRequest(
    val gymMachine: String,
    val name: String,
    val durationOfExecutionInSeconds: Int,
    val durationOfRestInSeconds: Int,
    val intensity: Int,
    val distance: Int
)
