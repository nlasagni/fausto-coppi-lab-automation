/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.adapters.ConsultingRepository
import it.unibo.lss.fcla.consulting.application.adapters.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.requests.ExamineAllSummariesForMemberRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.requests.ReceiveAthleticTrainerConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.requests.ReceiveBiomechanicalConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.requests.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.requests.ReceivePhysiotherapyConsultingRequest
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 *
 * This is a concrete implementation of [IController]. This class take the requests
 * provided from the UI and execute the operations in the use case layer.
 */
class ConsultingController(
    private val consultingRepository: ConsultingRepository,
    private val freelancerRepository: FreelancerRepository,
    private val presenter: IPresenter
) : IController {

    private val consultingUseCases: ConsultingUseCases =
        ConsultingUseCases(
            consultingRepository,
            freelancerRepository,
            presenter
        )

    /**
     * Method used to execute the operation based on the given [request]
     */
    override fun execute(request: IRequest) {
        try {
            when (request) {
                is ReceivePhysiotherapyConsultingRequest ->
                    consultingUseCases.receivePhysiotherapyConsulting(
                        consultingId = request.consultingId,
                        memberId = request.memberId,
                        consultingDate = request.consultingDate,
                        freelancerId = request.freelancerId,
                        description = request.description
                    )
                is ReceiveNutritionistConsultingRequest ->
                    consultingUseCases.receiveNutritionistConsulting(
                        consultingId = request.consultingId,
                        memberId = request.memberId,
                        consultingDate = request.consultingDate,
                        freelancerId = request.freelancerId,
                        description = request.description
                    )
                is ReceiveAthleticTrainerConsultingRequest ->
                    consultingUseCases.receiveAthleticTrainerConsulting(
                        consultingId = request.consultingId,
                        memberId = request.memberId,
                        consultingDate = request.consultingDate,
                        freelancerId = request.freelancerId,
                        description = request.description
                    )
                is ReceiveBiomechanicalConsultingRequest ->
                    consultingUseCases.receiveBiomechanicalConsulting(
                        consultingId = request.consultingId,
                        memberId = request.memberId,
                        consultingDate = request.consultingDate,
                        freelancerId = request.freelancerId,
                        description = request.description
                    )
                is ExamineAllSummariesForMemberRequest ->
                    consultingUseCases.retrieveProfile(
                        memberId = request.memberId
                    )
                else -> throw ConsultingException("Bad Request")
            }
        } catch (e: ConsultingException) {
            presenter.onError(e)
        }
    }
}
