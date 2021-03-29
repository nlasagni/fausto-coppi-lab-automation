package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.application.presentation.IResponse

/**
 * @author Stefano Braggion
 */
interface IPresenter {
    fun result(response: IResponse)
}