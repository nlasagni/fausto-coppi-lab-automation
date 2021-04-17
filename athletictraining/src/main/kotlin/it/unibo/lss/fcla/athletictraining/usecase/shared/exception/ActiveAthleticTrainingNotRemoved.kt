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
 * Thrown to indicate that has not been possible to remove the desired athletic training.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class ActiveAthleticTrainingNotRemoved :
    UseCaseException("An error occurred while removing the desired athletic training.")
