package it.unibo.lss.fcla.consulting.application.controllers

import it.unibo.lss.fcla.consulting.application.persistence.ConsultingRepository
import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceivePhysiotherapyConsultingRequest
import it.unibo.lss.fcla.consulting.usecases.ConsultingShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.IPresenter

/**
 * @author Stefano Braggion
 */
class ConsultingController(private val presenter: IPresenter) : BaseController() {

    private val consultingUseCases: ConsultingUseCases =
        ConsultingUseCases(ConsultingRepository(EventStore()), presenter)

    /**
     *
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
                else -> TODO()
            }
        }catch (e: ConsultingShouldHaveAUniqueId) {

        }
    }

}
