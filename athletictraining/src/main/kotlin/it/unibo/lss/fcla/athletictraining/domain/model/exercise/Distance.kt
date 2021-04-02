package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.DistanceDoesNotRespectRange
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance.Companion.MAX
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance.Companion.MIN

/**
 * ValueObject representing the distance made during an [Exercise] expressed in meters.
 *
 * The Distance must be inside the range [[MIN], [MAX]], otherwise a
 * [DistanceDoesNotRespectRange] is thrown.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
data class Distance(val value: Int) {

    companion object {
        /**
         * The minimum value allowed for a [Distance].
         */
        const val MIN = 0
        /**
         * The maximum value allowed for a [Distance].
         */
        const val MAX = Int.MAX_VALUE
    }

    init {
        if (isNotInRange()) {
            throw DistanceDoesNotRespectRange()
        }
    }

    /**
     * Checks if this distance is not inside the [[MIN], [MAX]] range.
     */
    private fun isNotInRange() = value !in MIN..MAX

    /**
     * Operator that sums [other] distance to this distance.
     * If the sum result exceeds the [MAX] upper bound,
     * a [DistanceDoesNotRespectRange] is thrown.
     */
    operator fun plus(other: Distance): Distance {
        return Distance(value + other.value)
    }

    /**
     * Operator that subtracts [other] distance to this distance.
     * If the subtraction result is lower than the [MIN] lower bound,
     * a [DistanceDoesNotRespectRange] is thrown.
     */
    operator fun minus(other: Distance): Distance {
        return Distance(value - other.value)
    }
}
