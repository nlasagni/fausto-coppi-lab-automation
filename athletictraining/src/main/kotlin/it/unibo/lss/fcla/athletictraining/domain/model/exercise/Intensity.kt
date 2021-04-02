package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.IntensityDoesNotRespectRange
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity.Companion.HIGH
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity.Companion.LOW

/**
 * ValueObject representing the intensity of an [Exercise].
 *
 * The Intensity must be inside the range [[LOW], [HIGH]], otherwise a
 * [IntensityDoesNotRespectRange] is thrown.
 *
 * The default value of the Intensity is [LOW].
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
data class Intensity(val value: Int = LOW) {

    companion object {
        /**
         * The minimum value allowed for an [Intensity].
         */
        const val LOW = 1
        /**
         * The normal value of an [Intensity].
         */
        const val NORMAL = 5
        /**
         * The maximum value allowed for an [Intensity].
         */
        const val HIGH = 10
    }

    init {
        if (isNotInRange()) {
            throw IntensityDoesNotRespectRange()
        }
    }

    /**
     * Checks if this intensity is not inside the [[LOW], [HIGH]] range.
     */
    private fun isNotInRange() = value !in LOW..HIGH

    /**
     * Operator that sums [other] intensity to this intensity.
     * If the sum result exceeds the [HIGH] upper bound,
     * an [IntensityDoesNotRespectRange] is thrown.
     */
    operator fun plus(other: Intensity): Intensity {
        return Intensity(value + other.value)
    }

    /**
     * Operator that subtracts [other] intensity to this intensity.
     * If the subtraction result is lower than the [LOW] lower bound,
     * an [IntensityDoesNotRespectRange] is thrown.
     */
    operator fun minus(other: Intensity): Intensity {
        return Intensity(value - other.value)
    }
}
