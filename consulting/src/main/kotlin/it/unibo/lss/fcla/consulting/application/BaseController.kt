package it.unibo.lss.fcla.consulting.application

import it.unibo.lss.fcla.consulting.presentation.IRequest
import it.unibo.lss.fcla.consulting.presentation.IResponse

/**
 * @author Stefano Braggion
 */
abstract class BaseController {

    abstract fun execute(request: IRequest) : IResponse
}