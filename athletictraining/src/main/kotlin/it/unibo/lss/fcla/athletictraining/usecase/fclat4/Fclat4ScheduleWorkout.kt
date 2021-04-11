package it.unibo.lss.fcla.athletictraining.usecase.fclat4

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.GymOpenChecker
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.GymClosedAtSchedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository

/**
 * Schedules a workout for an athletic training, refers to requirement FCLAT-4.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat4ScheduleWorkout(
    useCaseOutput: ScheduleWorkoutOutput,
    private val gymOpenChecker: GymOpenChecker,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val repository: ActiveAthleticTrainingRepository
) : ScheduleWorkoutInput,
    AthleticTrainingManagement<ScheduleWorkoutRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: ScheduleWorkoutRequest): ActiveAthleticTraining {
        if (!gymOpenChecker.isGymOpenForSchedule(request.schedule)) {
            throw GymClosedAtSchedule()
        }
        val activeAthleticTraining =
            repository.findById(ActiveAthleticTrainingId(request.activeAthleticTrainingId))
                ?: throw ActiveAthleticTrainingNotFound()
        activeAthleticTraining.scheduleWorkout(
            WorkoutId(request.workoutId),
            request.schedule
        )
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        return repository.update(activeAthleticTraining)
    }
}
