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
 * Thrown to indicate that the desired period extension ends before the current period of
 * an ActiveAthleticTraining.
 *
 * @author Nicola Lasagni on 01/03/2021.
 */
class PeriodExtensionCannotEndBeforeCurrentPeriod :
    DomainException("The end of the extension of a period cannot occur before the current one.")
