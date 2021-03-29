package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.ui.ConsoleUI

class StarterApplication {

    fun configureAndStart() {
        val presenter = PresenterImpl()
        val freelancerController = FreelancerController(presenter)
        val ui = ConsoleUI(freelancerController)
        presenter.register(ui)
        ui.startUI()
    }
}

fun main() {
    StarterApplication().configureAndStart()
}
