package it.unibo.lss.fcla.consulting.application.presentation

import it.unibo.lss.fcla.consulting.application.presentation.consulting.ConsultingErrorResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerAvailabilityResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerErrorResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.MessageResponse
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidMember
import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.usecases.IPresenter
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerAvailabilityFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerErrorFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerFacade

/**
 * @author Stefano Braggion
 */
class PresenterImpl : IPresenter {

    private val viewList: MutableList<IView> = mutableListOf()

    override fun onResult(result: BaseFacade) {
        val translatedResponse = transformResultIntoResponse(result)
        viewList.forEach {
            it.render(translatedResponse)
        }
    }

    override fun onError(error: Exception) {
        val translatedError = transformErrorIntoResponse(error)
        viewList.forEach {
            it.render(translatedError)
        }
    }

    override fun register(view: IView) {
        viewList.add(view)
    }

    private fun transformErrorIntoResponse(error: Exception) : IResponse {
        return when (error) {
            is ConsultingMustHaveAValidMember ->
                ConsultingErrorResponse(
                    message = error.message ?: ""
                )
            else -> TODO()
        }
    }

    private fun transformResultIntoResponse(result: BaseFacade) : IResponse {
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
            else -> MessageResponse("Bad response")
        }
    }
}
