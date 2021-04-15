package it.unibo.lss.fcla.athletictraining.usecase.fclat13

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat13CheckWorkouts] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface CheckWorkoutsOutput : UseCaseOutput<Collection<Workout>>
