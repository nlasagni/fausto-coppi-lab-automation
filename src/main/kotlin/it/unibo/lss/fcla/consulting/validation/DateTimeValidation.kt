package it.unibo.lss.fcla.consulting.validation

import it.unibo.lss.fcla.consulting.models.Date
import it.unibo.lss.fcla.consulting.models.Time

class DateTimeValidation {

    companion object {
        fun isDateValid(date: Date){
            require(date.year >= 1900)
            require(date.month in 1..12)
            require(date.day in 1..31)
        }

        fun isTimeValid(time: Time){
            require(time.hours in 0..23)
            require(time.minutes in 0..59)
        }
    }

}