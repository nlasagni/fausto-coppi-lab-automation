package it.unibo.lss.fcla.consulting.models

import it.unibo.lss.fcla.consulting.validation.DateTimeValidation

/**
 * @author Stefano Braggion
 *
 * Value object representing a time in consulting context
 */
data class Time(val hours: Int, val minutes: Int) {

    init {
        DateTimeValidation.isTimeValid(this)
    }

}