package it.unibo.lss.fcla.athleticPreparation.domain

data class DomainDate(val year: Int = 2020, val month: Int = 1, val day: Int = 1) {

    override fun toString(): String {
        return "Date($year-$month-$day)"
    }

    override fun equals(other: Any?): Boolean =
        (other is DomainDate)
                && (year == other.year)
                && (month == other.month)
                && (day == other.day)
}
