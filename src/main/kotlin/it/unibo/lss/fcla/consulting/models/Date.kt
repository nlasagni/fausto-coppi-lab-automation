package it.unibo.lss.fcla.consulting.models

/**
 * @author Stefano Braggion
 *
 * Value object representing a date in consulting context
 */
data class Date(val year: Int, val month: Int, val day: Int){

    init {
        require(year > 1900)
        require(month in 1..12)
        require(day in 1..31)
    }

    override fun equals(other: Any?): Boolean {
        //TODO fixme
        return true
    }

}