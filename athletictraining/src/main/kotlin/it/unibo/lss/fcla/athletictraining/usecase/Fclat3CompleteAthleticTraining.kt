package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedWorkout
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.usecase.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.exception.ActiveAthleticTrainingNotRemoved
import it.unibo.lss.fcla.athletictraining.usecase.model.CompleteAthleticTrainingRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.CompletedAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.IdGenerator
import it.unibo.lss.fcla.athletictraining.usecase.port.output.UseCaseOutput

/**
 * Completes an athletic training, refers to requirement FCLAT-3.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat3CompleteAthleticTraining(
    useCaseOutput: UseCaseOutput<CompletedAthleticTraining>,
    private val idGenerator: IdGenerator,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val activeAthleticTrainingRepository: ActiveAthleticTrainingRepository,
    private val completedAthleticTrainingRepository: CompletedAthleticTrainingRepository
) : AthleticTrainingManagement<CompleteAthleticTrainingRequest, CompletedAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: CompleteAthleticTrainingRequest): CompletedAthleticTraining {
        val athleticTraining =
            activeAthleticTrainingRepository.findById(request.id)
                ?: throw ActiveAthleticTrainingNotFound()
        val snapshot = athleticTraining.snapshot()
        val completedWorkouts =
            snapshot.scheduledWorkouts.map { CompletedWorkout(it.workout, it.schedule) }
        val completedAthleticTraining = CompletedAthleticTraining(
            CompletedAthleticTrainingId(idGenerator.generate()),
            snapshot.athleticTrainer,
            snapshot.member,
            snapshot.purpose,
            snapshot.period,
            completedWorkouts
        )
        val activeRemoved = activeAthleticTrainingRepository.remove(athleticTraining)
        if (!activeRemoved) {
            throw ActiveAthleticTrainingNotRemoved()
        }
        memberProfileUpdater.updateProfile(completedAthleticTraining.madeForMember())
        return completedAthleticTrainingRepository.add(completedAthleticTraining)
    }
}
