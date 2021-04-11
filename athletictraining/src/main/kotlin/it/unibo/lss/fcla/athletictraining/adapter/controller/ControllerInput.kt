package it.unibo.lss.fcla.athletictraining.adapter.controller

/**
 * The input port of the controller component.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface ControllerInput {
    /**
     * Executes the specified [request] by mean of related use cases.
     */
    fun executeRequest(request: ControllerRequest)
}
