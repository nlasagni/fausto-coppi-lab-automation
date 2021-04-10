package it.unibo.lss.fcla.athletictraining.usecase.model

/**
 * Class that represents the response generated from the
 * processing of a use case input request.
 *
 * @property response The generated response.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class UseCaseResponse<T>(val response: T)