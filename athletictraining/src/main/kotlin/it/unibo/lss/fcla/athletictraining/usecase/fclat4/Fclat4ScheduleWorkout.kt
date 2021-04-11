package it.unibo.lss.fcla.athletictraining.usecase.fclat4

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.GymOpenChecker
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculator
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.GymClosedAtSchedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Schedules a workout for an athletic training, refers to requirement FCLAT-4.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat4ScheduleWorkout(
    useCaseOutput: ScheduleWorkoutOutput,
    private val gymOpenChecker: GymOpenChecker,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val workoutTotalDurationCalculator: WorkoutTotalDurationCalculator,
    private val activeAthleticTrainingRepository: ActiveAthleticTrainingRepository,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : ScheduleWorkoutInput,
    AthleticTrainingManagement<ScheduleWorkoutRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: ScheduleWorkoutRequest): ActiveAthleticTraining {
        val workoutId = WorkoutId(request.workoutId)
        val workout = workoutRepository.findById(workoutId) ?: throw WorkoutNotFound()
        val workoutSnapshot = workout.snapshot()
        val exerciseIds = workoutSnapshot.exercises
        val exercises = mutableListOf<Exercise>()
        for (exerciseId in exerciseIds) {
            val exercise = exerciseRepository.findById(exerciseId)
            if (exercise != null) {
                exercises.add(exercise)
            }
        }
        val workoutDuration = workoutTotalDurationCalculator.computeTotalDuration(exercises)
        val schedule = Schedule(request.day, request.time, request.time.plusSeconds(workoutDuration.seconds))
        if (!gymOpenChecker.isGymOpenForSchedule(schedule)) {
            throw GymClosedAtSchedule()
        }
        val activeAthleticTraining =
            activeAthleticTrainingRepository.findById(ActiveAthleticTrainingId(request.activeAthleticTrainingId))
                ?: throw ActiveAthleticTrainingNotFound()
        activeAthleticTraining.scheduleWorkout(
            WorkoutId(request.workoutId),
            schedule
        )
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        return activeAthleticTrainingRepository.update(activeAthleticTraining)
    }
}
