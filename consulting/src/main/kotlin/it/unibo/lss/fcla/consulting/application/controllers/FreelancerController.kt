package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.application.persistence.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateBiomechanicalFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreatePhysiotherapistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.DeleteFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.GetFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.UpdateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 *
 * This is a concrete implementation of [BaseController]. This class take the requests
 * provided form the UI and execute the operations in the use case layer.
 */
class FreelancerController(private val presenter: IPresenter) : IController {

    private val freelancerUseCases: FreelancerUseCases =
        FreelancerUseCases(FreelancerRepository(EventStore()), presenter)

    /**
     * Internal enum used to create the consulting request
     */
    internal enum class ConsultingType {
        AthleticTrainer, Nutritionist, Physiotherapist, Biomechanical
    }

    /**
     * Method used to execute the operation based on the given [request]
     */
    override fun execute(request: IRequest) {
        try {
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
        } catch (e: ConsultingException) {
            presenter.onError(e)
        }
    }
}
