package it.unibo.lss.fcla.athletictraining.usecase.fclat4

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat4ScheduleWorkout] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface ScheduleWorkoutOutput : UseCaseOutput<ActiveAthleticTraining>
