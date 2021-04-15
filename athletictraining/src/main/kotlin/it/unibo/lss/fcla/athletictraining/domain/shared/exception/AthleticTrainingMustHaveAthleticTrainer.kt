/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the athletic training is missing a proper athletic trainer reference.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class AthleticTrainingMustHaveAthleticTrainer :
    DomainException("An athletic training must have an Athletic Trainer that prepares it.")
