package it.unibo.lss.fcla.athletictraining.usecase.shared.input

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.UseCaseException
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseError
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseResponse
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * Abstract class that represent a generic use case input
 * that process a request and generates a response which is
 * then passed to the use case output.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
abstract class AthleticTrainingManagement<REQUEST, RESPONSE>(
    private val useCaseOutput: UseCaseOutput<RESPONSE>
) : UseCaseInput<REQUEST> {

    /**
     * Process the specified [request]. If a [UseCaseException] or a [DomainException]
     * is thrown, a [UseCaseError] instance is passed to the use case output.
     */
    override fun execute(request: REQUEST) {
        try {
            val response = processRequest(request)
            useCaseOutput.handleResponse(UseCaseResponse(response))
        } catch (useCaseException: UseCaseException) {
            useCaseOutput.handleError(UseCaseError(useCaseException.localizedMessage))
        } catch (domainException: DomainException) {
            useCaseOutput.handleError(UseCaseError(domainException.localizedMessage))
        }
    }

    /**
     * Process the use case input [request] and generates
     * the related [RESPONSE].
     */
    abstract fun processRequest(request: REQUEST): RESPONSE
}
