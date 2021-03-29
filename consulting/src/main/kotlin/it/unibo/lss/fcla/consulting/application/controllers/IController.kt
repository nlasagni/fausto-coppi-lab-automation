package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.presentation.IRequest

/**
 * @author Stefano Braggion
 *
 * Interface that each controller must implement
 */
interface IController {
    fun execute(request: IRequest)
}
