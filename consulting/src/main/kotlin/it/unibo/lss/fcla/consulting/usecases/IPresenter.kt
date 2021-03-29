package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade

/**
 * @author Stefano Braggion
 */
interface IPresenter {
    fun onResult(result: BaseFacade)
    fun onError(error: Exception)
    fun register(view: IView)
}
