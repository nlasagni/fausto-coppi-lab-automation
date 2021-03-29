package it.unibo.lss.fcla.consulting.ui

import it.unibo.lss.fcla.consulting.application.presentation.IResponse

interface IView {
    fun render(response: IResponse)
}