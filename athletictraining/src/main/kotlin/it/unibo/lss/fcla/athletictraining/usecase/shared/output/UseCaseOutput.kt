/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.output

import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseError
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseResponse

/**
 * The output port of the Use Case layer.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
interface UseCaseOutput<T> {

    /**
     * Handles the [response] generated from the processing of the input port request.
     */
    fun handleResponse(response: UseCaseResponse<T>)

    /**
     * Handles the [error] generated from the processing of the input port request.
     */
    fun handleError(error: UseCaseError)
}
