package it.unibo.lss.fcla.athletictraining.usecase.fclat9

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Removes a previously chosen exercise for a workout, refers to requirement FCLAT-9.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat9RemoveExerciseFromWorkout(
    useCaseOutput: RemoveExerciseFromWorkoutOutput,
    private val workoutRepository: WorkoutRepository
) : RemoveExerciseFromWorkoutInput,
    AthleticTrainingManagement<RemoveExerciseRequest, Workout>(useCaseOutput) {

    override fun processRequest(request: RemoveExerciseRequest): Workout {
        val workoutSnapshot = workoutRepository.findById(WorkoutId(request.workoutId))
            ?: throw WorkoutNotFound()
        val workout = Workout.rehydrate(workoutSnapshot)
        workout.cancelExercise(request.exerciseIndex)
        workoutRepository.update(workout.snapshot())
        return workout
    }
}
