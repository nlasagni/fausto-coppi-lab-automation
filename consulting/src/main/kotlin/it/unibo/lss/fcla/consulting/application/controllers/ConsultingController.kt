package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.persistence.ConsultingRepository
import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.application.persistence.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveAthleticTrainerConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveBiomechanicalConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceivePhysiotherapyConsultingRequest
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidId
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryMustHaveAValidFreelancer
import it.unibo.lss.fcla.consulting.usecases.ConsultingShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 *
 * This is a concrete implementation of [BaseController]. This class take the requests
 * provided form the UI and execute the operations in the use case layer.
 */
class ConsultingController(private val presenter: IPresenter) : IController {

    private val consultingUseCases: ConsultingUseCases =
        ConsultingUseCases(
            ConsultingRepository(EventStore()),
            FreelancerRepository(EventStore()),
            presenter)

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
                else -> TODO()
            }
        } catch (e: ConsultingShouldHaveAUniqueId) {
            presenter.onError(e)
        } catch (e: ConsultingMustHaveAValidId) {
            presenter.onError(e)
        } catch (e: ConsultingSummaryDescriptionCannotBeEmpty) {
            presenter.onError(e)
        } catch (e: ConsultingSummaryMustHaveAValidFreelancer) {
            presenter.onError(e)
        }
    }
}
