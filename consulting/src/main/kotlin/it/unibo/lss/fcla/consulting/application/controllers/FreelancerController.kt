/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.adapters.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.CreateBiomechanicalFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.CreatePhysiotherapistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.DeleteFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.GetFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.requests.UpdateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 *
 * This is a concrete implementation of [IController]. This class take the requests
 * provided from the UI and execute the operations in the use case layer.
 */
class FreelancerController(
    private val freelancerRepository: FreelancerRepository,
    private val presenter: IPresenter
) : IController {

    private val freelancerUseCases: FreelancerUseCases =
        FreelancerUseCases(freelancerRepository, presenter)

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
                else -> throw ConsultingException("Bad Request")
            }
        } catch (e: ConsultingException) {
            presenter.onError(e)
        }
    }
}
