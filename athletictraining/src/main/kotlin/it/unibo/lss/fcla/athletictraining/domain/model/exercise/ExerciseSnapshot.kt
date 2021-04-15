package it.unibo.lss.fcla.athletictraining.domain.model.exercise

/**
 * A snapshot class used to expose all the relevant information about an [Exercise].
 *
 * @property id The [ExerciseId] to which this snapshot refers.
 * @property name The name of the [Exercise] to which this snapshot refers.
 * @property durationOfExecution The [Duration] of execution of the exercise to which this snapshot refers.
 * @property durationOfRest The [Duration] of rest of the exercise to which this snapshot refers.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
data class ExerciseSnapshot(
    val id: ExerciseId,
    val name: String,
    val configuration: Configuration,
    val durationOfExecution: Duration,
    val durationOfRest: Duration
)
