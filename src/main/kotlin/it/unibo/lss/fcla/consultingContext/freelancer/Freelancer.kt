package it.unibo.lss.fcla.consultingContext.freelancer

import it.unibo.lss.fcla.consultingContext.common.AbstractAggregate
import it.unibo.lss.fcla.consultingContext.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.domain.events.FreelancerAvailabilityCreatedEvent
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents a freelancer
 */
class Freelancer(
    val freelancerId: FreelancerId
    ): AbstractAggregate() {

    private val availabilities = mutableListOf<Availability>()

    constructor(freelancerId: FreelancerId, firstName: String, lastName: String, role: FreelancerRole): this(freelancerId) {

    }

    init {
        //register event handlers
        this.register<FreelancerAvailabilityCreatedEvent>(this::applyEvent)
    }

    /**
     * Add an availability day and hours with given [newAvailabilityDate], [fromTime], [toTime]
     */
    fun addAvailability(newAvailabilityDate: Date, fromTime: LocalTime, toTime: LocalTime) {
        require(fromTime.isBefore(toTime))

        val exist = availabilities.firstOrNull { it.availabilityDate == newAvailabilityDate } != null
        if (exist) {
            throw ConsultingException("An availability already exists for date $newAvailabilityDate")
        }

        val availability = Availability(newAvailabilityDate, fromTime, toTime)
        availabilities.add(availability)
    }

    /**
     * Delete [availabilityDate] from freelancer availabilities
     */
    fun deleteAvailability(availabilityDate: Date) {
        availabilities.removeIf { it.availabilityDate == availabilityDate }
    }

    /**
     * Get the fromHours of the given [availabilityDate]
     */
    fun getAvailabilityFromHours(availabilityDate: Date): LocalTime? =
        availabilities.firstOrNull { it.availabilityDate == availabilityDate }?.fromTime

    /**
     * Get the toHours of the given [availabilityDate]
     */
    fun getAvailabilityToHours(availabilityDate: Date): LocalTime? =
        availabilities.firstOrNull { it.availabilityDate == availabilityDate }?.toTime

    //event handlers

    private fun applyEvent(event: FreelancerAvailabilityCreatedEvent) {

    }

}
