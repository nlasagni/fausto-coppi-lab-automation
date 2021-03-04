package it.unibo.lss.fcla.athleticpreparation.domain.model

import java.time.Duration

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Exercise(
        private val configuration: Configuration,
        private val durationOfExecution: Duration,
        private val durationOfRest: Duration
)