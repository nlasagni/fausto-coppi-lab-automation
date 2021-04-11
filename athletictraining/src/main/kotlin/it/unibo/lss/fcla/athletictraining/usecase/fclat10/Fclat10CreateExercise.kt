package it.unibo.lss.fcla.athletictraining.usecase.fclat10

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator

/**
 * Creates a new exercise, refers to requirement FCLAT-10.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat10CreateExercise(
    useCaseOutput: CreateExerciseOutput,
    private val idGenerator: IdGenerator,
    private val repository: ExerciseRepository
) : CreateExerciseInput,
    AthleticTrainingManagement<CreateExerciseRequest, Exercise>(useCaseOutput) {

    override fun processRequest(request: CreateExerciseRequest): Exercise {
        val configuration = Configuration(
            GymMachineId(request.gymMachine),
            Intensity(request.intensity),
            Distance(request.distance)
        )
        val exercise = Exercise(
            ExerciseId(idGenerator.generate()),
            request.name,
            configuration,
            Duration(request.durationOfExecution),
            Duration(request.durationOfRest)
        )
        return repository.add(exercise)
    }
}
