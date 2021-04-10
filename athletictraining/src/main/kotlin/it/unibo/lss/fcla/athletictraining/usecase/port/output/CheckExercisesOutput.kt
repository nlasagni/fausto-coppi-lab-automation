package it.unibo.lss.fcla.athletictraining.usecase.port.output

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise

/**
 * @author Nicola Lasagni on 10/04/2021.
 */
interface CheckExercisesOutput : UseCaseOutput<Collection<Exercise>>