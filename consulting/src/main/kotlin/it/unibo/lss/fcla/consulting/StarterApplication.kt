package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.application.adapters.ConsultingRepository
import it.unibo.lss.fcla.consulting.application.adapters.EventStore
import it.unibo.lss.fcla.consulting.application.adapters.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.controllers.ConsultingController
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.ui.ConsoleUI
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
    private fun setupSampleData(
        freelancerController: FreelancerController,
        consultingController: ConsultingController
    ) {

        val date = LocalDate.now()
        val time1 = LocalTime.now()
        val time2 = time1.plusHours(1)

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
                availabilityDate = date,
                fromTime = time1,
                toTime = time2
            )
        )
        println("Created availability for freelancer Mario Rossi")
        freelancerController.execute(
            CreateFreelancerAvailabilityForDayRequest(
                freelancerId = "F2",
                availabilityDate = date,
                fromTime = time1,
                toTime = time2
            )
        )
        println("Created availability for freelancer Giuseppe Bianchi")
        consultingController.execute(
            ReceiveNutritionistConsultingRequest(
                consultingId = "C1",
                memberId = "M1",
                consultingDate = date,
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
