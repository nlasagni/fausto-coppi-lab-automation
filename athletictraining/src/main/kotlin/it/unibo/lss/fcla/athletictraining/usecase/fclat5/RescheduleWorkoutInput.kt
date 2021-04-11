package it.unibo.lss.fcla.athletictraining.usecase.fclat5

import it.unibo.lss.fcla.athletictraining.usecase.shared.input.UseCaseInput

/**
 * The [UseCaseInput] port of the [Fclat5RescheduleWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface RescheduleWorkoutInput : UseCaseInput<RescheduleWorkoutRequest>