package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.model.BuildWorkoutRequest
import it.unibo.lss.fcla.athletictraining.usecase.model.UseCaseResponse
import it.unibo.lss.fcla.athletictraining.usecase.port.input.UseCaseInput
import it.unibo.lss.fcla.athletictraining.usecase.port.output.IdGenerator
import it.unibo.lss.fcla.athletictraining.usecase.port.output.UseCaseOutput
import it.unibo.lss.fcla.athletictraining.usecase.port.output.WorkoutRepository

/**
 * Builds a new workout, refers to requirement FCLAT-7.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat7BuildWorkout(
    useCaseOutput: UseCaseOutput<Workout>,
    private val idGenerator: IdGenerator,
    private val workoutRepository: WorkoutRepository
) : AthleticTrainingManagement<BuildWorkoutRequest, Workout>(useCaseOutput) {

    override fun processRequest(request: BuildWorkoutRequest): Workout {
        val workout = Workout(WorkoutId(idGenerator.generate()), request.name)
        return workoutRepository.add(workout)
    }

}