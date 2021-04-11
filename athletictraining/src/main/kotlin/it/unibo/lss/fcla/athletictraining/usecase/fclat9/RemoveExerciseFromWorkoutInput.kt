package it.unibo.lss.fcla.athletictraining.usecase.fclat9

import it.unibo.lss.fcla.athletictraining.usecase.shared.input.UseCaseInput

/**
 * The [UseCaseInput] port of the [Fclat9RemoveExerciseFromWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface RemoveExerciseFromWorkoutInput : UseCaseInput<RemoveExerciseRequest>