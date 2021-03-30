package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.persistence.ConsultingRepository
import it.unibo.lss.fcla.consulting.application.persistence.FreelancerRepository
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ExamineAllSummariesForMemberRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveAthleticTrainerConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveBiomechanicalConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceivePhysiotherapyConsultingRequest
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 *
 * This is a concrete implementation of [BaseController]. This class take the requests
 * provided form the UI and execute the operations in the use case layer.
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
