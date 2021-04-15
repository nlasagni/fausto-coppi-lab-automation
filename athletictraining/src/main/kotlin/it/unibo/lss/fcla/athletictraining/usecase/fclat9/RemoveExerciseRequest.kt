/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat9

/**
 * Class that represents the request coming from outer layer of
 * removing an exercise from a workout.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
data class RemoveExerciseRequest(val workoutId: String, val exerciseIndex: Int)
