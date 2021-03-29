package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.application.controllers.ConsultingController
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.ui.ConsoleUI

class StarterApplication {

    fun configureAndStart() {
        val presenter = PresenterImpl()
        val freelancerController = FreelancerController(presenter)
        val consultingController = ConsultingController(presenter)
        val ui = ConsoleUI()
        presenter.register(ui)
    }

}

fun main() {
    StarterApplication().configureAndStart()
}