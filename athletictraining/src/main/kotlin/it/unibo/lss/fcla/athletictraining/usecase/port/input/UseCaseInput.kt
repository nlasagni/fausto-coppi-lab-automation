package it.unibo.lss.fcla.athletictraining.usecase.port.input

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