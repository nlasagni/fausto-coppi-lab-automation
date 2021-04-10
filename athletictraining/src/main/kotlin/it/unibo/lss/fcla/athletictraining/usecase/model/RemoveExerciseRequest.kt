package it.unibo.lss.fcla.athletictraining.usecase.model

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * Class that represents the request coming from outer layer of
 * removing an exercise from a workout.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
data class RemoveExerciseRequest(val workoutId: WorkoutId, val exerciseIndex: Int)
