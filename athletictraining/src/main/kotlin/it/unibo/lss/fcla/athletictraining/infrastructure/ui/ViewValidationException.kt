/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import java.lang.Exception

/**
 * Thrown to indicate that an input read by the view is not correct.
 *
 * @property message The description of the exception.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class ViewValidationException(message: String) : Exception(message)
