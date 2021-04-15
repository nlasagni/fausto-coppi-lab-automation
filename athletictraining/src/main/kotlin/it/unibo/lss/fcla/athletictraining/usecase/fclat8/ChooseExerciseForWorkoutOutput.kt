package it.unibo.lss.fcla.athletictraining.usecase.fclat8

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat8ChooseExerciseForWorkout] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface ChooseExerciseForWorkoutOutput : UseCaseOutput<Workout>
