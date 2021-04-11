package it.unibo.lss.fcla.athletictraining.usecase.fclat6

import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

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
) : CancelScheduledWorkoutInput,
    AthleticTrainingManagement<CancelScheduledWorkoutRequest, Boolean>(useCaseOutput) {

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
