package it.unibo.lss.fcla.athletictraining.usecase.fclat10

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat10CreateExercise] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface CreateExerciseOutput : UseCaseOutput<Exercise>
