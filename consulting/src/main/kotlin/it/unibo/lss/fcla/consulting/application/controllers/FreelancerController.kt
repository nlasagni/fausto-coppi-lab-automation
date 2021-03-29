package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.application.persistence.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateBiomechanicalFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreatePhysiotherapistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.DeleteFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.GetFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.UpdateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 */
class FreelancerController : BaseController() {

    private val presenter: IPresenter = PresenterImpl()
    private val freelancerUseCases: FreelancerUseCases =
        FreelancerUseCases(FreelancerRepository(EventStore()), presenter)

    /**
     *
     */
    override fun execute(request: IRequest) {
        when (request) {
            is CreateAthleticTrainerFreelancerRequest ->
                freelancerUseCases.createAthleticTrainer(
                    request.freelancerId,
                    request.firstName,
                    request.lastName
                )
            is CreateNutritionistFreelancerRequest ->
                freelancerUseCases.createNutritionist(
                    request.freelancerId,
                    request.firstName,
                    request.lastName
                )
            is CreatePhysiotherapistFreelancerRequest ->
                freelancerUseCases.createPhysiotherapist(
                    request.freelancerId,
                    request.firstName,
                    request.lastName
                )
            is CreateBiomechanicalFreelancerRequest ->
                freelancerUseCases.createBiomechanical(
                    request.freelancerId,
                    request.firstName,
                    request.lastName
                )
            is CreateFreelancerAvailabilityForDayRequest ->
                freelancerUseCases.createFreelancerAvailabilityForDay(
                    request.freelancerId,
                    request.availabilityDate,
                    request.fromTime,
                    request.toTime
                )
            is DeleteFreelancerAvailabilityForDayRequest ->
                freelancerUseCases.deleteFreelancerAvailabilityForDay(
                    request.freelancerId,
                    request.availabilityDate
                )
            is UpdateFreelancerAvailabilityForDayRequest ->
                freelancerUseCases.updateFreelancerAvailabilityForDay(
                    request.freelancerId,
                    request.availabilityDate,
                    request.fromTime,
                    request.toTime
                )
            is GetFreelancerAvailabilityForDayRequest ->
                freelancerUseCases.getFreelancerAvailabilityForDay(
                    request.freelancerId,
                    request.availabilityDate
                )
            else -> TODO()
        }
    }
}
