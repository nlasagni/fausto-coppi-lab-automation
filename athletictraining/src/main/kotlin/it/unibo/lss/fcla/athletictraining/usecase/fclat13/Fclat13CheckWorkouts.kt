package it.unibo.lss.fcla.athletictraining.usecase.fclat13

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Checks all the created workouts, refers to requirement FCLAT-13.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class Fclat13CheckWorkouts(
    useCaseOutput: CheckWorkoutsOutput,
    private val repository: WorkoutRepository
) : CheckWorkoutsInput,
    AthleticTrainingManagement<CheckWorkoutsRequest, Collection<Workout>>(useCaseOutput) {

    override fun processRequest(request: CheckWorkoutsRequest): Collection<Workout> {
        return repository.findAll().map { Workout.rehydrate(it) }
    }
}
