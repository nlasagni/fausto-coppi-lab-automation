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

    /**
     * Method called by the use case layer whenever the result of an operation is ready
     */
    fun onResult(result: BaseFacade)

    /**
     * Method called by the use case layer whenever an error occurred during the execution of an operation
     */
    fun onError(error: ConsultingException)

    /**
     * Method used by a [IView] to register in this presenter.
     * The registration allow the view to be notified when a result or an error occurred
     */
    fun register(view: IView)
}
