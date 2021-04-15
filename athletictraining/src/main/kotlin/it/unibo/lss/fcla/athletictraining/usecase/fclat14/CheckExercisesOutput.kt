package it.unibo.lss.fcla.athletictraining.usecase.fclat14

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat14CheckExercises] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface CheckExercisesOutput : UseCaseOutput<Collection<Exercise>>
