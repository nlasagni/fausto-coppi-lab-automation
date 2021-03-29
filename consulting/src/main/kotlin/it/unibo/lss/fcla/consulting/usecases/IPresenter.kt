package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.ui.IViewModel
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade

/**
 * @author Stefano Braggion
 */
interface IPresenter {
    fun onResult(result: BaseFacade)
    fun register(viewModel: IViewModel)
}
