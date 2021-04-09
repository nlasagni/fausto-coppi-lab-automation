package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception.DurationMustBeGreaterThanZero

/**
 * A Value Object representing the duration of an [Exercise] expressed in seconds.
 *
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class Duration(val seconds: Int) {

    init {
        if (seconds <= 0) {
            throw DurationMustBeGreaterThanZero()
        }
    }

}