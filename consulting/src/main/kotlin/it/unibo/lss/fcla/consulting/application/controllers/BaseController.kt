package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.IResponse

/**
 * @author Stefano Braggion
 */
abstract class BaseController {

    abstract fun execute(request: IRequest) : IResponse
}