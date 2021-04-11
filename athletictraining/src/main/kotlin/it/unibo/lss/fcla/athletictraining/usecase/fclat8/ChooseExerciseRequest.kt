package it.unibo.lss.fcla.athletictraining.usecase.fclat8

/**
 * Class that represents the request coming from outer layer of
 * choosing an exercise for a workout.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class ChooseExerciseRequest(val workoutId: String, val exerciseId: String)
