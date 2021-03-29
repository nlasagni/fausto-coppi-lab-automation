package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.*

/**
 * @author Stefano Braggion
 */
class FreelancerController() : BaseController() {

    /**
     *
     */
    override fun execute(request: IRequest): IResponse {
        when (request) {
            is CreateAthleticTrainerFreelancerRequest -> TODO()
            is CreateNutritionistFreelancerRequest -> TODO()
            is CreatePhysiotherapistFreelancerRequest -> TODO()
            is CreateBiomechanicalFreelancerRequest -> TODO()
            is CreateFreelancerAvailabilityForDayRequest -> TODO()
            is DeleteFreelancerAvailabilityForDayRequest -> TODO()
            is UpdateFreelancerAvailabilityForDayRequest -> TODO()
            is GetFreelancerAvailabilityForDayRequest -> TODO()
            else -> TODO()
        }
    }
}