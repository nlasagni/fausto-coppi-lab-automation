package it.unibo.lss.fcla.consulting.models

import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents the freelancer availability based on days and hours
 */
class Availability(val availabilityDate: Date, val fromTime: LocalTime, val toTime: LocalTime){

    private var availabilityHours: AvailabilityHours = AvailabilityHours(fromTime, toTime)

    init {
        if(DateTimeValidation.isAvailabilityTimeValid(availabilityHours.fromTime, availabilityHours.toTime))
            throw IllegalArgumentException("Availability end time must be greater than start time")
    }

    fun getAvailabilityFromHours(): LocalTime = availabilityHours.fromTime

    fun getAvailabilityToHours(): LocalTime = availabilityHours.toTime

    fun updateAvailability(fromTime: LocalTime, toTime: LocalTime){
        if()
    }

}