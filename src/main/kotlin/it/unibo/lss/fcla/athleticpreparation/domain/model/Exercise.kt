package it.unibo.lss.fcla.athleticpreparation.domain.model

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Exercise(
        private val configuration: Configuration,
        private val duration: Duration,
        private val rest: Duration
)