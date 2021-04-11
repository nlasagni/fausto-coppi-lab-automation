package it.unibo.lss.fcla.athletictraining.usecase.fclat12

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository

/**
 * Checks all the currently active athletic trainings, refers to requirement FCLAT-12.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class Fclat12CheckActiveAthleticTrainings(
    useCaseOutput: CheckActiveAthleticTrainingsOutput,
    private val repository: ActiveAthleticTrainingRepository
) : CheckActiveAthleticTrainingsInput,
    AthleticTrainingManagement<CheckActiveAthleticTrainingsRequest, Collection<ActiveAthleticTraining>>(useCaseOutput) {

    override fun processRequest(request: CheckActiveAthleticTrainingsRequest): Collection<ActiveAthleticTraining> {
        return repository.findAllByMemberId(request.memberId)
    }
}
