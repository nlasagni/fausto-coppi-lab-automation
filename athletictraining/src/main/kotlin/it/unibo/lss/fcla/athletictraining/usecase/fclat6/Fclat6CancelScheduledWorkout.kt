package it.unibo.lss.fcla.athletictraining.usecase.fclat6

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculator
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Cancels a workout previously scheduled for an athletic training, refers to requirement FCLAT-6.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat6CancelScheduledWorkout(
    useCaseOutput: CancelScheduledWorkoutOutput,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val workoutTotalDurationCalculator: WorkoutTotalDurationCalculator,
    private val activeAthleticTrainingRepository: ActiveAthleticTrainingRepository,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : CancelScheduledWorkoutInput,
    AthleticTrainingManagement<CancelScheduledWorkoutRequest, Boolean>(useCaseOutput) {

    override fun processRequest(request: CancelScheduledWorkoutRequest): Boolean {
        val activeAthleticTraining = activeAthleticTrainingRepository.findById(
            ActiveAthleticTrainingId(request.activeAthleticTrainingId)
        ) ?: throw ActiveAthleticTrainingNotFound()
        val workoutId = WorkoutId(request.workoutId)
        val workout = workoutRepository.findById(workoutId)
            ?: throw WorkoutNotFound()
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
        activeAthleticTraining.cancelScheduledWorkout(workoutId, schedule)
        activeAthleticTrainingRepository.update(activeAthleticTraining)
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        return true
    }
}
