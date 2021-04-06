package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import java.time.Duration

/**
 * @author Nicola Lasagni on 03/04/2021.
 */
class ExerciseSnapshot(
    val id: ExerciseId,
    val name: String,
    val configuration: Configuration,
    val durationOfExecution: Duration,
    val durationOfRest: Duration
)
