package it.unibo.lss.fcla.consulting.application.presentation

import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerAvailabilityResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.FreelancerResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.MessageResponse
import it.unibo.lss.fcla.consulting.ui.IViewModel
import it.unibo.lss.fcla.consulting.usecases.IPresenter
import it.unibo.lss.fcla.consulting.usecases.facades.BaseFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerAvailabilityFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerFacade

/**
 * @author Stefano Braggion
 */
class PresenterImpl : IPresenter {

    private val viewModelList: List<IViewModel> = listOf()

    override fun onResult(result: BaseFacade) {
        val translatedResponse = transformResultIntoResponse(result)

        viewModelList.forEach {
            it.render(translatedResponse)
        }
    }

    override fun register(viewModel: IViewModel) {
        viewModelList + viewModel
    }

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
            else -> MessageResponse("Bad response")
        }
    }
}
