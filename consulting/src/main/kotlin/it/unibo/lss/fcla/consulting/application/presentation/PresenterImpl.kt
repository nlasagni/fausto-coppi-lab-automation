/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.presentation

import it.unibo.lss.fcla.consulting.application.presentation.consulting.responses.ConsultingErrorResponse
import it.unibo.lss.fcla.consulting.application.presentation.consulting.responses.ConsultingResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.responses.FreelancerAvailabilityResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.responses.FreelancerErrorResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.responses.FreelancerResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.responses.MessageResponse
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.usecases.BaseFacade
import it.unibo.lss.fcla.consulting.usecases.IPresenter
import it.unibo.lss.fcla.consulting.usecases.consulting.facades.ConsultingFacade
import it.unibo.lss.fcla.consulting.usecases.freelancer.facades.FreelancerAvailabilityFacade
import it.unibo.lss.fcla.consulting.usecases.freelancer.facades.FreelancerErrorFacade
import it.unibo.lss.fcla.consulting.usecases.freelancer.facades.FreelancerFacade

/**
 * @author Stefano Braggion
 *
 * This is the concrete implementation of the [IPresenter] interface defined in the use case layer.
 * This object is then injected into the use case so it interact only with the defined interface.
 */
class PresenterImpl : IPresenter {

    private val viewList: MutableList<IView> = mutableListOf()

    /**
     * Method called when the [result] of a use case operation is ready.
     * Each registered view is notified about the result.
     */
    override fun onResult(result: BaseFacade) {
        val translatedResponse = transformResultIntoResponse(result)
        viewList.forEach {
            it.render(translatedResponse)
        }
    }

    /**
     * Method called when the use case throw an [error]
     */
    override fun onError(error: ConsultingException) {
        val translatedError = transformErrorIntoResponse(error)
        viewList.forEach {
            it.render(translatedError)
        }
    }

    /**
     * Method used to register a view to this presenter
     */
    override fun register(view: IView) {
        viewList.add(view)
    }

    /**
     * Method used to transform the [error] raised by the use case layer into a [IResponse]
     */
    private fun transformErrorIntoResponse(error: ConsultingException): IResponse {
        return ConsultingErrorResponse(error.message ?: "")
    }

    /**
     * Method used to transform the [result] of an operation into a [IResponse]
     */
    private fun transformResultIntoResponse(result: BaseFacade): IResponse {
        return when (result) {
            is FreelancerFacade ->
                FreelancerResponse(
                    freelancerId = result.freelancerId,
                    firstName = result.firstName,
                    lastName = result.lastName,
                    role = result.role
                )
            is FreelancerAvailabilityFacade ->
                FreelancerAvailabilityResponse(
                    availabilityDate = result.availabilityDate,
                    fromTime = result.fromTime,
                    toTime = result.toTime
                )
            is FreelancerErrorFacade ->
                FreelancerErrorResponse(
                    message = result.message
                )
            is ConsultingFacade ->
                ConsultingResponse(
                    consultingId = result.consultingId,
                    memberId = result.memberId,
                    consultingDate = result.consultingDate,
                    freelancerId = result.freelancerId,
                    consultingType = result.consultingType,
                    description = result.description
                )
            else -> MessageResponse("Bad response")
        }
    }
}
