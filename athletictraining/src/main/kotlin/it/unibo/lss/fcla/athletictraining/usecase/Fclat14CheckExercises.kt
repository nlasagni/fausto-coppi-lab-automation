package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.usecase.model.CheckExercisesRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.CheckExercisesOutput
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ExerciseRepository

/**
 * Checks all the created exercises, refers to requirement FCLAT-14.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class Fclat14CheckExercises(
    useCaseOutput: CheckExercisesOutput,
    private val repository: ExerciseRepository
) : AthleticTrainingManagement<CheckExercisesRequest, Collection<Exercise>>(useCaseOutput) {

    override fun processRequest(request: CheckExercisesRequest): Collection<Exercise> {
        return repository.findAll()
    }
}
