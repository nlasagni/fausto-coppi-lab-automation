package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.usecase.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.model.RemoveExerciseRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.UseCaseOutput
import it.unibo.lss.fcla.athletictraining.usecase.port.output.WorkoutRepository

/**
 * Removes a previously chosen exercise for a workout, refers to requirement FCLAT-9.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat9RemoveExerciseFromWorkout(
    useCaseOutput: UseCaseOutput<Workout>,
    private val workoutRepository: WorkoutRepository
) : AthleticTrainingManagement<RemoveExerciseRequest, Workout>(useCaseOutput) {

    override fun processRequest(request: RemoveExerciseRequest): Workout {
        val workout = workoutRepository.findById(request.workoutId)
            ?: throw WorkoutNotFound()
        workout.cancelExercise(request.exerciseIndex)
        return workoutRepository.update(workout)
    }
}
