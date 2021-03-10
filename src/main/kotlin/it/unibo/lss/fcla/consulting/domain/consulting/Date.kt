package it.unibo.lss.fcla.consulting.domain.consulting

/**
 * @author Stefano Braggion
 *
 * Value object representing a date in the consulting context
 */
data class Date(val year: Int, val month: Int, val day: Int) {

    companion object {
        const val MIN_YEAR: Int = 1900
        const val MIN_MONTH: Int = 1
        const val MAX_MONTH: Int = 12
        const val MIN_DAY: Int = 1
        const val MAX_DAY: Int = 31
    }

    /**
     * Validation of date
     */
    init {
        require(year > MIN_YEAR)
        require(month in MIN_MONTH..MAX_MONTH)
        require(day in MIN_DAY..MAX_DAY)
    }

    /**
     * Check if the current date is equal to [other]
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Date) return false

        return other.year == year &&
            other.month == month &&
            other.day == day
    }
}
