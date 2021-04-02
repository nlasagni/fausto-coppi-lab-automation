package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.DistanceIsLowerThanZero

/**
 * ValueObject representing the distance made during an [Exercise].
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
data class Distance(val value: Double) {

    init {
        if (!isGreaterThanZero()) {
            throw DistanceIsLowerThanZero()
        }
    }

    private fun isGreaterThanZero() = value > 0

}