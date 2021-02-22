package it.unibo.lss.fcla.consulting.validation

import it.unibo.lss.fcla.consulting.models.Date

class DateTimeValidation {

    companion object DateValidation{
        fun isDateValid(date: Date){
            require(date.year >= 1900)
            require(date.month in 1..12)
            require(date.day in 1..31)
        }
    }
}