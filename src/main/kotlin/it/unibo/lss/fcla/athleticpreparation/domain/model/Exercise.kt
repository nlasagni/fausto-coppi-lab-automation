package it.unibo.lss.fcla.athleticpreparation.domain.model

import java.time.Duration

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Exercise(
        val configuration: Configuration,
        val durationOfExecution: Duration,
        val durationOfRest: Duration
)
