package it.unibo.lss.fcla.athletictraining.usecase.fclat6

import it.unibo.lss.fcla.athletictraining.usecase.shared.input.UseCaseInput

/**
 * The [UseCaseInput] port of the [Fclat6CancelScheduledWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface CancelScheduledWorkoutInput : UseCaseInput<CancelScheduledWorkoutRequest>