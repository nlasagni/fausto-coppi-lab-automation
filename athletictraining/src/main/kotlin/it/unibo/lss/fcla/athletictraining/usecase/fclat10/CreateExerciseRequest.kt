package it.unibo.lss.fcla.athletictraining.usecase.fclat10

/**
 * Class that represents the request coming from outer layer of
 * creating a new exercise.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class CreateExerciseRequest(
    val gymMachine: String,
    val name: String,
    val durationOfExecution: Int,
    val durationOfRest: Int,
    val intensity: Int,
    val distance: Int
)
