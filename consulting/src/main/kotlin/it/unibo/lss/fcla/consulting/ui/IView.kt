package it.unibo.lss.fcla.consulting.ui

import it.unibo.lss.fcla.consulting.application.presentation.IResponse

/**
 * @author Stefano Braggion
 *
 * Interface that each view must implement
 */
interface IView {
    fun render(response: IResponse)
}
