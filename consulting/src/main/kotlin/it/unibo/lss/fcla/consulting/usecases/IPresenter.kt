package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade

/**
 * @author Stefano Braggion
 *
 * Interface that each presenter must implement
 */
interface IPresenter {
    fun onResult(result: BaseFacade)
    fun onError(error: ConsultingException)
    fun register(view: IView)
}
