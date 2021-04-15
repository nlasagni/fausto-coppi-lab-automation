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
