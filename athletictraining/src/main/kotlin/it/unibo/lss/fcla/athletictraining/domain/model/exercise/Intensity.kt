package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.IntensityDoesNotRespectRange
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity.Companion.MAX
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity.Companion.MIN

/**
 * ValueObject representing the intensity of an [Exercise].
 *
 * The Intensity must be inside the range [[MIN], [MAX]], otherwise a
 * [IntensityDoesNotRespectRange] is thrown.
 *
 * The default value of the Intensity is [MIN].
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
data class Intensity(val value: Int = MIN) {

    companion object {
        /**
         * The minimum value allowed for an [Intensity].
         */
        const val MIN = 1
        /**
         * The maximum value allowed for an [Intensity].
         */
        const val MAX = 10
    }

    init {
        if (isNotInRange()) {
            throw IntensityDoesNotRespectRange()
        }
    }

    /**
     * Checks if this intensity is not inside the [[MIN], [MAX]] range.
     */
    private fun isNotInRange() = value !in MIN..MAX

    /**
     * Operator that sums [other] intensity to this intensity.
     * If the sum result exceeds the [MAX] upper bound,
     * an [IntensityDoesNotRespectRange] is thrown.
     */
    operator fun plus(other: Intensity): Intensity {
        return Intensity(value + other.value)
    }

    /**
     * Operator that subtracts [other] intensity to this intensity.
     * If the subtraction result is lower than the [MIN] lower bound,
     * an [IntensityDoesNotRespectRange] is thrown.
     */
    operator fun minus(other: Intensity): Intensity {
        return Intensity(value - other.value)
    }
}
