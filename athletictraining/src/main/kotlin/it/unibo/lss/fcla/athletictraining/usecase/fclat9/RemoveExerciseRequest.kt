package it.unibo.lss.fcla.athletictraining.usecase.fclat9

/**
 * Class that represents the request coming from outer layer of
 * removing an exercise from a workout.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
data class RemoveExerciseRequest(val workoutId: String, val exerciseIndex: Int)
