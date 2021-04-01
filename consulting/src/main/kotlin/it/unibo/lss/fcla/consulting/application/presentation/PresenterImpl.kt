package it.unibo.lss.fcla.consulting.application.presentation

import it.unibo.lss.fcla.consulting.application.presentation.consulting.ConsultingErrorResponse
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ConsultingResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerAvailabilityResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerErrorResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.MessageResponse
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.ui.IView
import it.unibo.lss.fcla.consulting.usecases.IPresenter
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade
import it.unibo.lss.fcla.consulting.usecases.facades.ConsultingFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerAvailabilityFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerErrorFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerFacade

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
