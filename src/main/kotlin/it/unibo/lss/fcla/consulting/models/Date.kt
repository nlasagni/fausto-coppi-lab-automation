package it.unibo.lss.fcla.consulting.models

import it.unibo.lss.fcla.consulting.validation.DateTimeValidation

/**
 * @author Stefano Braggion
 *
 * Value object representing a date in consulting context
 */
data class Date(val year: Int, val month: Int, val day: Int){

    init {
        DateTimeValidation.isDateValid(this)
    }

    override fun equals(other: Any?): Boolean {
        //TODO fixme
        return true
    }

}