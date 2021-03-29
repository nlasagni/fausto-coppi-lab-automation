package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.ui.ConsoleUI

class StarterApplication {

    fun configureAndStart() {
        /* val presenter = PresenterImpl()
        val freelancerController = FreelancerController(presenter)
        val consultingController = ConsultingController(presenter)
        val ui = ConsoleUI() */

        val ui = ConsoleUI()
        ui.startUI()
    }
}

fun main() {
    StarterApplication().configureAndStart()
}
