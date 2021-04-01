package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.presentation.IRequest

/**
 * @author Stefano Braggion
 *
 * Interface that each controller must implement
 */
interface IController {
    /**
     * Method that take an [IRequest] from the UI and pass it
     * to the use case layer for the execution.
     */
    fun execute(request: IRequest)
}
