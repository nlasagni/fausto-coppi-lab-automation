package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.IntensityDoesNotRespectRange

/**
 * ValueObject representing the intensity of an [Exercise].
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
data class Intensity(val value: Int) {

    companion object {
        /**
         * The minimum value allowed for the intensity of an [Exercise]
         */
        const val Min = 1
        /**
         * The maximum value allowed for the intensity of an [Exercise]
         */
        const val Max = 10
    }

    init {
        if (isNotInRange()) {
            throw IntensityDoesNotRespectRange()
        }
    }

    private fun isNotInRange() = value !in Min..Max

}