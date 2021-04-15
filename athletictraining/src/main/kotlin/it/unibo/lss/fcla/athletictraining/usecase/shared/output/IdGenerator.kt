/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.output

/**
 * A generator of unique ids as [String].
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
interface IdGenerator {
    /**
     * Generates a unique id as [String].
     * @return The generated id.
     */
    fun generate(): String
}
