package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.GymOpenChecker
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.usecase.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.exception.GymClosedAtSchedule
import it.unibo.lss.fcla.athletictraining.usecase.model.RescheduleWorkoutRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.UseCaseOutput

/**
 * Reschedules a workout for an athletic training, refers to requirement FCLAT-5.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat5RescheduleWorkout(
    useCaseOutput: UseCaseOutput<ActiveAthleticTraining>,
    private val gymOpenChecker: GymOpenChecker,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val repository: ActiveAthleticTrainingRepository
) : AthleticTrainingManagement<RescheduleWorkoutRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: RescheduleWorkoutRequest): ActiveAthleticTraining {
        if (!gymOpenChecker.isGymOpenForSchedule(request.newSchedule)) {
            throw throw GymClosedAtSchedule()
        }
        val activeAthleticTraining =
            repository.findById(ActiveAthleticTrainingId(request.activeAthleticTrainingId))
                ?: throw ActiveAthleticTrainingNotFound()
        activeAthleticTraining.rescheduleWorkout(
            WorkoutId(request.workoutId),
            request.oldSchedule,
            request.newSchedule
        )
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        return repository.update(activeAthleticTraining)
    }
}
