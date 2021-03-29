package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade
import it.unibo.lss.fcla.consulting.ui.IView

/**
 * @author Stefano Braggion
 */
interface IPresenter {
    fun onResult(result: BaseFacade)
    fun register(view: IView)
}