package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.OverlappingAthleticTrainingsChecker
import it.unibo.lss.fcla.athletictraining.usecase.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.exception.OverlappingAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.model.ExtendAthleticTrainingPeriodRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ExtendAthleticTrainingPeriodOutput

/**
 * Extends the period of an athletic training, refers to requirement FCLAT-2.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat2ExtendAthleticTrainingPeriod(
    useCaseOutput: ExtendAthleticTrainingPeriodOutput,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val repository: ActiveAthleticTrainingRepository,
    private val overlappingService: OverlappingAthleticTrainingsChecker
) : AthleticTrainingManagement<ExtendAthleticTrainingPeriodRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: ExtendAthleticTrainingPeriodRequest): ActiveAthleticTraining {
        val activeAthleticTraining =
            repository.findById(ActiveAthleticTrainingId(request.id))
                ?: throw ActiveAthleticTrainingNotFound()
        val snapshot = activeAthleticTraining.snapshot()
        val newPeriod = snapshot.period.changeEndDay(request.newPeriodEnd)
        val memberOtherAthleticTrainings =
            repository.findAllByMemberId(activeAthleticTraining.member)
                .filter { it.id != activeAthleticTraining.id }
        if (overlappingService.existsOverlappingAthleticTraining(memberOtherAthleticTrainings, newPeriod)) {
            throw OverlappingAthleticTraining()
        }
        activeAthleticTraining.postponeTrainingPeriodEnd(request.newPeriodEnd)
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        return repository.update(activeAthleticTraining)
    }
}
