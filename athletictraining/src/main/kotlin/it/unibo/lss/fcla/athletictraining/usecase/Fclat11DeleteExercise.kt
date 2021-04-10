package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.usecase.exception.ExerciseNotFound
import it.unibo.lss.fcla.athletictraining.usecase.model.DeleteExerciseRequest
import it.unibo.lss.fcla.athletictraining.usecase.port.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.port.output.UseCaseOutput

/**
 * Creates a previously created exercise, refers to requirement FCLAT-11.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat11DeleteExercise(
    useCaseOutput: UseCaseOutput<Boolean>,
    private val repository: ExerciseRepository
) : AthleticTrainingManagement<DeleteExerciseRequest, Boolean>(useCaseOutput) {

    override fun processRequest(request: DeleteExerciseRequest): Boolean {
        val exercise = repository.findById(request.exerciseId) ?: throw ExerciseNotFound()
        return repository.remove(exercise)
    }
}
