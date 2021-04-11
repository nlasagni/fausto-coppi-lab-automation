package it.unibo.lss.fcla.athletictraining.usecase.fclat3

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedWorkout
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotRemoved
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.CompletedAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator

/**
 * Completes an athletic training, refers to requirement FCLAT-3.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat3CompleteAthleticTraining(
    useCaseOutput: CompleteAthleticTrainingOutput,
    private val idGenerator: IdGenerator,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val activeAthleticTrainingRepository: ActiveAthleticTrainingRepository,
    private val completedAthleticTrainingRepository: CompletedAthleticTrainingRepository
) : CompleteAthleticTrainingInput,
    AthleticTrainingManagement<CompleteAthleticTrainingRequest, CompletedAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: CompleteAthleticTrainingRequest): CompletedAthleticTraining {
        val athleticTraining =
            activeAthleticTrainingRepository.findById(ActiveAthleticTrainingId(request.id))
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
