package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.usecase.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.model.CancelScheduledWorkoutRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.CancelScheduledWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.port.output.WorkoutRepository

/**
 * Cancels a workout previously scheduled for an athletic training, refers to requirement FCLAT-6.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat6CancelScheduledWorkout(
    useCaseOutput: CancelScheduledWorkoutOutput,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val activeAthleticTrainingRepository: ActiveAthleticTrainingRepository,
    private val workoutRepository: WorkoutRepository
) : AthleticTrainingManagement<CancelScheduledWorkoutRequest, Boolean>(useCaseOutput) {

    override fun processRequest(request: CancelScheduledWorkoutRequest): Boolean {
        val activeAthleticTraining =
            activeAthleticTrainingRepository.findById(request.activeAthleticTrainingId)
                ?: throw ActiveAthleticTrainingNotFound()
        val workout = workoutRepository.findById(request.workoutId) ?: throw WorkoutNotFound()
        activeAthleticTraining.cancelScheduledWorkout(workout.id, request.schedule)
        activeAthleticTrainingRepository.update(activeAthleticTraining)
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        return true
    }
}
