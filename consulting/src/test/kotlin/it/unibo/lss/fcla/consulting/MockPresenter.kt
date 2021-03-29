package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.usecases.IPresenter
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade

class MockPresenter : IPresenter {
    override fun onResult(result: BaseFacade) {
    }

    override fun register(view: IView) {

    }
}
