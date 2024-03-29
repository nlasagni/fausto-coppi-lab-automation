/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance.Companion.MAX
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance.Companion.ZERO
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.DistanceDoesNotRespectRange

/**
 * ValueObject representing the distance made during an [Exercise] expressed in meters.
 *
 * The Distance must be inside the range [[ZERO], [MAX]], otherwise a
 * [DistanceDoesNotRespectRange] is thrown.
 *
 * @property meters The meters amount of this Distance.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
data class Distance(val meters: Int = ZERO) {

    companion object {
        /**
         * The minimum value allowed for a [Distance].
         */
        const val ZERO = 0
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
     * Checks if this distance is not inside the [[ZERO], [MAX]] range.
     */
    private fun isNotInRange() = meters !in ZERO..MAX

    /**
     * Operator that sums [other] distance to this distance.
     * If the sum result exceeds the [MAX] upper bound,
     * a [DistanceDoesNotRespectRange] is thrown.
     */
    operator fun plus(other: Distance): Distance {
        return Distance(meters + other.meters)
    }

    /**
     * Operator that subtracts [other] distance to this distance.
     * If the subtraction result is lower than the [ZERO] lower bound,
     * a [DistanceDoesNotRespectRange] is thrown.
     */
    operator fun minus(other: Distance): Distance {
        return Distance(meters - other.meters)
    }
}
