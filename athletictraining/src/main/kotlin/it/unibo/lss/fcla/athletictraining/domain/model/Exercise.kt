package it.unibo.lss.fcla.athletictraining.domain.model

import java.time.Duration

/**
 * The Exercise that has to be done in conjunction with
 * others during a [Workout].
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Exercise(
    val configuration: Configuration,
    val durationOfExecution: Duration,
    val durationOfRest: Duration
)
