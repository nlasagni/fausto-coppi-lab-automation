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
 * Thrown to indicate that the schedule has a end time that happens before the start time.
 *
 * @author Nicola Lasagni on 01/04/2021.
 */
class BeginningOfScheduleCannotBeAfterEnd :
    DomainException("The beginning of a Schedule cannot be after its end.")
