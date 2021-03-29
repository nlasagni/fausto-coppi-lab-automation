package it.unibo.lss.fcla.consulting

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest

class FreelancerControllerTest : FreeSpec({
    "request create new athletic trainer test" - {
        val request = CreateAthleticTrainerFreelancerRequest(
            freelancerId = "001",
            firstName = "Mario",
            lastName = "Rossi"
        )

        val controller = FreelancerController()
        controller.execute(request)


    }
})