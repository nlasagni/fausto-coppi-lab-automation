package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.application.controllers.ConsultingController
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.persistence.ConsultingRepository
import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.application.persistence.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.ui.ConsoleUI
import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.ui.MenuUtils
import java.time.LocalDate
import java.time.LocalTime

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
        val consultingRepository = ConsultingRepository(EventStore())
        val freelancerRepository = FreelancerRepository(EventStore())
        val freelancerController = FreelancerController(freelancerRepository, presenter)
        val consultingController = ConsultingController(consultingRepository, freelancerRepository, presenter)
        val ui = ConsoleUI(freelancerController, consultingController)
        presenter.register(ui)

        if (sampleData) {
            setupSampleData(freelancerController, consultingController)
        }

        ui.startUI()
    }

    /**
     * Setup initial data
     */
    private fun setupSampleData(freelancerController: FreelancerController, consultingController: ConsultingController) {
        freelancerController.execute(
            CreateAthleticTrainerFreelancerRequest(
                freelancerId = "F1",
                firstName = "Mario",
                lastName = "Rossi"
            )
        )
        println("Created freelancer Mario Rossi of type AthleticTrainer")
        freelancerController.execute(
            CreateNutritionistFreelancerRequest(
                freelancerId = "F2",
                firstName = "Giuseppe",
                lastName = "Bianchi"
            )
        )
        println("Created freelancer Giuseppe Bianchi of type Nutritionist")
        freelancerController.execute(
            CreateFreelancerAvailabilityForDayRequest(
                freelancerId = "F1",
                availabilityDate = LocalDate.of(2021, 1, 1),
                fromTime = LocalTime.of(10, 0),
                toTime = LocalTime.of(11, 0)
            )
        )
        println("Created availability for freelancer Mario Rossi")
        freelancerController.execute(
            CreateFreelancerAvailabilityForDayRequest(
                freelancerId = "F2",
                availabilityDate = LocalDate.of(2021, 1, 2),
                fromTime = LocalTime.of(9, 0),
                toTime = LocalTime.of(11, 0)
            )
        )
        println("Created availability for freelancer Giuseppe Bianchi")
        consultingController.execute(
            ReceiveNutritionistConsultingRequest(
                consultingId = "C1",
                memberId = "M1",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "F2",
                description = "Sample description of consulting C1"
            )
        )
        println("Created consulting C1")
    }
}

/**
 * Entry point of the application
 */
fun main() {
    val startWithDemo = MenuUtils.readFromConsole("Start application with sample data? (y/n)").toLowerCase() == "y"
    StarterApplication().configureAndStart(startWithDemo)
}
