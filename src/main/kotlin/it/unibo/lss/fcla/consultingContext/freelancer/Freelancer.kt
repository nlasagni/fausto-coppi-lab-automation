package it.unibo.lss.fcla.consultingContext.freelancer

import it.unibo.lss.fcla.consultingContext.common.AbstractAggregate
import it.unibo.lss.fcla.consultingContext.domain.exceptions.ConsultingException
import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.domain.events.FreelancerAvailabilityCreatedEvent
import it.unibo.lss.fcla.consultingContext.domain.events.FreelancerAvailabilityDeletedEvent
import it.unibo.lss.fcla.consultingContext.domain.events.FreelancerAvailabilityUpdatedEvent
import it.unibo.lss.fcla.consultingContext.domain.exceptions.FreelancerAvailabilityAlreadyExist
import it.unibo.lss.fcla.consultingContext.domain.exceptions.FreelancerAvailabilityNotValidTime
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
        this.register<FreelancerAvailabilityUpdatedEvent>(this::applyEvent)
        this.register<FreelancerAvailabilityDeletedEvent>(this::applyEvent)
    }

    /**
     * Add an availability day and hours with given [newAvailabilityDate], [fromTime], [toTime]
     */
    fun addAvailability(newAvailabilityDate: Date, fromTime: LocalTime, toTime: LocalTime) {
        if(!fromTime.isBefore(toTime))
            throw FreelancerAvailabilityNotValidTime()

        val exist = availabilities.firstOrNull { it.availabilityDate == newAvailabilityDate } != null
        if (exist)
            throw FreelancerAvailabilityAlreadyExist()

        raiseEvent(FreelancerAvailabilityCreatedEvent(freelancerId, newAvailabilityDate, fromTime, toTime))
    }

    /**
     * Delete [availabilityDate] from freelancer availabilities
     */
    fun deleteAvailability(availabilityDate: Date) {
        raiseEvent(FreelancerAvailabilityDeletedEvent(freelancerId, availabilityDate))
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

    /**
     * Event handler for create
     */
    private fun applyEvent(event: FreelancerAvailabilityCreatedEvent) {
        val availability = Availability(event.availabilityDate, event.fromTime, event.toTime)
        availabilities.add(availability)
    }

    /**
     * Event handler for update
     */
    private fun applyEvent(event: FreelancerAvailabilityUpdatedEvent) {

    }

    /**
     * Event handler for delete
     */
    private fun applyEvent(event: FreelancerAvailabilityDeletedEvent) {
        availabilities.removeIf { it.availabilityDate == event.availabilityDate }
    }

}
