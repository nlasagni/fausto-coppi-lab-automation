package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.model.CheckActiveAthleticTrainingsRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.CheckActiveAthleticTrainingsOutput

/**
 * Checks all the currently active athletic trainings, refers to requirement FCLAT-12.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class Fclat12CheckActiveAthleticTrainings(
    useCaseOutput: CheckActiveAthleticTrainingsOutput,
    private val repository: ActiveAthleticTrainingRepository
) : AthleticTrainingManagement<CheckActiveAthleticTrainingsRequest, Collection<ActiveAthleticTraining>>(useCaseOutput) {

    override fun processRequest(request: CheckActiveAthleticTrainingsRequest): Collection<ActiveAthleticTraining> {
        return repository.findAllByMemberId(request.memberId)
    }
}
