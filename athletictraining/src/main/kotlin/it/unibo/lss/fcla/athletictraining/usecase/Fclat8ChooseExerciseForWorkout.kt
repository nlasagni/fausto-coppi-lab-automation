package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.usecase.exception.ExerciseNotFound
import it.unibo.lss.fcla.athletictraining.usecase.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.model.ChooseExerciseRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ChooseExerciseForWorkoutOutput
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.WorkoutRepository

/**
 * Choose an exercise for a workout, refers to requirement FCLAT-8.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat8ChooseExerciseForWorkout(
    useCaseOutput: ChooseExerciseForWorkoutOutput,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : AthleticTrainingManagement<ChooseExerciseRequest, Workout>(useCaseOutput) {

    override fun processRequest(request: ChooseExerciseRequest): Workout {
        val workout = workoutRepository.findById(request.workoutId)
            ?: throw WorkoutNotFound()
        val exercise = exerciseRepository.findById(request.exerciseId)
            ?: throw ExerciseNotFound()
        workout.prepareExercise(exercise.id)
        return workoutRepository.update(workout)
    }
}
