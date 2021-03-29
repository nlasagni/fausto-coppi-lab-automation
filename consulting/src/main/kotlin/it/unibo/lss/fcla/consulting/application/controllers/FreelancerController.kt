package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.application.persistence.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.application.presentation.PresenterImpl
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.*
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases

/**
 * @author Stefano Braggion
 */
class FreelancerController() : BaseController() {

    private val freelancerPresenter: PresenterImpl = PresenterImpl()
    private val freelancerUseCases: FreelancerUseCases =
        FreelancerUseCases(FreelancerRepository(EventStore()), presenter = freelancerPresenter)

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