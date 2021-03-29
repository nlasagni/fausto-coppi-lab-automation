package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.presentation.IRequest

/**
 * @author Stefano Braggion
 */
abstract class BaseController {
    abstract fun execute(request: IRequest)
}
