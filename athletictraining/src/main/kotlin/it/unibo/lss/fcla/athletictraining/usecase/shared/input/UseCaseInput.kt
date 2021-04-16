/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.input

/**
 * The input port of the Use Case layer.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
interface UseCaseInput<T> {

    /**
     * Executes the task related to the [request] specified.
     */
    fun execute(request: T)
}
