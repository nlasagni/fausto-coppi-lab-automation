package it.unibo.lss.fcla.consulting.ui

import it.unibo.lss.fcla.consulting.application.presentation.IResponse

/**
 * @author Stefano Braggion
 */
interface IViewModel {
    fun render(response: IResponse)
}
