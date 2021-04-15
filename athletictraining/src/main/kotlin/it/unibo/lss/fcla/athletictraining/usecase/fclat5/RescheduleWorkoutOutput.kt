package it.unibo.lss.fcla.athletictraining.usecase.fclat5

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat5RescheduleWorkout] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface RescheduleWorkoutOutput : UseCaseOutput<ActiveAthleticTraining>
