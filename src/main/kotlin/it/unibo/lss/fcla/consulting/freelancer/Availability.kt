package it.unibo.lss.fcla.consulting.freelancer

import it.unibo.lss.fcla.consulting.domain.consulting.Date
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents the freelancer availability based on days and hours
 */
class Availability(val availabilityDate: Date, val fromTime: LocalTime, val toTime: LocalTime) {

    private var availabilityHours: AvailabilityHours = AvailabilityHours(fromTime, toTime)

    /**
     * Get the availability fromHours
     */
    fun getAvailabilityFromHours(): LocalTime = availabilityHours.fromTime

    /**
     * Get the availability toHours
     */
    fun getAvailabilityToHours(): LocalTime = availabilityHours.toTime

    /**
     * Update the availability with given [fromTime] and [toTime]
     *
     */
    fun updateAvailability(fromTime: LocalTime, toTime: LocalTime) {
        require(fromTime.isBefore(toTime))

        availabilityHours = AvailabilityHours(fromTime, toTime)
    }
}
