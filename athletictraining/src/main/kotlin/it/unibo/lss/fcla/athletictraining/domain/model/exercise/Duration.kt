package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.DurationMustBeGreaterThanZero

/**
 * A Value Object representing the duration of an [Exercise] expressed in seconds.
 *
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
data class Duration(val seconds: Long) {

    companion object {

        private const val MINUTES_TO_SECONDS = 60
        private const val HOURS_TO_SECONDS = MINUTES_TO_SECONDS * 60

        fun ofMinutes(minutes: Long): Duration {
            return Duration(minutes * MINUTES_TO_SECONDS)
        }

        fun ofHours(hours: Long): Duration {
            return Duration(hours * HOURS_TO_SECONDS)
        }
    }

    init {
        if (seconds <= 0) {
            throw DurationMustBeGreaterThanZero()
        }
    }
}
