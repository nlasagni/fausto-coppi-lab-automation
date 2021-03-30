package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.application.controllers.ConsultingController
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.ui.ConsoleUI
import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.ui.MenuUtils

/**
 * @author Stefano Braggion
 *
 */
class StarterApplication {

    /**
     * Configure the application components
     */
    fun configureAndStart(sampleData: Boolean) {
        val presenter = PresenterImpl()
        val freelancerController = FreelancerController(presenter)
        val consultingController = ConsultingController(presenter)
        val ui = ConsoleUI(freelancerController, consultingController)
        presenter.register(ui)

        if (sampleData) {
            setupSampleData()
        }

        ui.startUI()
    }

    /**
     * Setup initial data
     */
    private fun setupSampleData() {

    }
}

/**
 * Entry point of the application
 */
fun main() {
    val startWithDemo = MenuUtils.readFromConsole("Start application with sample data? (y/n)").toLowerCase() == "y"
    StarterApplication().configureAndStart(startWithDemo)
}
