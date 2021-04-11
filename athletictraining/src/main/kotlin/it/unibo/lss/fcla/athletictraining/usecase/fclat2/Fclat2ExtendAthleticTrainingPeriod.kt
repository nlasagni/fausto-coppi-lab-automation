package it.unibo.lss.fcla.athletictraining.usecase.fclat2

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.OverlappingAthleticTrainingsChecker
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.OverlappingAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository

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
) : ExtendAthleticTrainingPeriodInput,
    AthleticTrainingManagement<ExtendAthleticTrainingPeriodRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: ExtendAthleticTrainingPeriodRequest): ActiveAthleticTraining {
        val activeAthleticTrainingSnapshot =
            repository.findById(ActiveAthleticTrainingId(request.id))
                ?: throw ActiveAthleticTrainingNotFound()
        val newPeriod = activeAthleticTrainingSnapshot.period.changeEndDay(request.newPeriodEnd)
        val memberOtherAthleticTrainings =
            repository.findAllByMemberId(activeAthleticTrainingSnapshot.member)
                .filter { it.id != activeAthleticTrainingSnapshot.id }
                .map { ActiveAthleticTraining.rehydrate(it) }
        if (overlappingService.existsOverlappingAthleticTraining(memberOtherAthleticTrainings, newPeriod)) {
            throw OverlappingAthleticTraining()
        }
        val activeAthleticTraining = ActiveAthleticTraining.rehydrate(activeAthleticTrainingSnapshot)
        activeAthleticTraining.postponeTrainingPeriodEnd(request.newPeriodEnd)
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        repository.update(activeAthleticTraining.snapshot())
        return activeAthleticTraining
    }
}
