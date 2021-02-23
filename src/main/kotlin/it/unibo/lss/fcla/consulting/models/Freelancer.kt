package it.unibo.lss.fcla.consulting.models

import it.unibo.lss.fcla.consulting.exceptions.ConsultingException
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents a freelancer
 */
class Freelancer(val firstName: String, val lastName: String, val role: FreelancerRole) {

    private val availabilities = mutableListOf<Availability>()

    init {
        require(!firstName.isNullOrEmpty())
        require(!lastName.isNullOrEmpty())
    }

    /**
     * Add an availability day and hours with given [newAvailabilityDate], [fromTime], [toTime]
     */
    fun addAvailability(newAvailabilityDate: Date, fromTime: LocalTime, toTime: LocalTime){
        require(fromTime.isBefore(toTime))

        val exist = availabilities.firstOrNull { it.availabilityDate == newAvailabilityDate } != null
        if(exist)
            throw ConsultingException("An availability already exists for date $newAvailabilityDate")

        val availability = Availability(newAvailabilityDate, fromTime, toTime)
        availabilities.add(availability)
    }

    /**
     * Delete [availabilityDate] from freelancer availabilities
     */
    fun deleteAvailability(availabilityDate: Date){
        availabilities.removeIf{ it.availabilityDate == availabilityDate }
    }

}