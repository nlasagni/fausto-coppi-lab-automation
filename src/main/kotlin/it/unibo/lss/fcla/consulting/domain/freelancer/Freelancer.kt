package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.exceptions.*
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents a freelancer
 */
class Freelancer(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : AbstractAggregate() {

    private val availabilities = mutableListOf<Availability>()

    init {

        if(firstName.isEmpty())
            throw FreelancerFirstNameCannotBeNull()
        if(lastName.isEmpty())
            throw FreelancerLastNameCannotBeNull()

        this.register<FreelancerAvailabilityCreatedEvent>(this::applyEvent)
        this.register<FreelancerAvailabilityDeletedEvent>(this::applyEvent)
    }

    /**
     * Add an availability day and hours with given [newAvailabilityDate], [fromTime], [toTime]
     */
    fun addAvailability(newAvailabilityDate: Date, fromTime: LocalTime, toTime: LocalTime) {
        if (!fromTime.isBefore(toTime)) throw FreelancerAvailabilityNotValidTime()

        val exist = availabilities.firstOrNull { it.availabilityDate == newAvailabilityDate } != null
        if (exist) throw FreelancerAvailabilityAlreadyExist()

        raiseEvent(FreelancerAvailabilityCreatedEvent(freelancerId, newAvailabilityDate, fromTime, toTime))
    }

    fun updateAvailability(availabilityDate: Date, fromTime: LocalTime, toTime: LocalTime) {
        if (!fromTime.isBefore(toTime)) throw FreelancerAvailabilityNotValidTime()

        val exist = availabilities.firstOrNull { it.availabilityDate == availabilityDate } != null
        if (!exist) throw FreelancerAvailabilityDoesNotExist()

        raiseEvent(FreelancerAvailabilityDeletedEvent(freelancerId, availabilityDate))
        raiseEvent(FreelancerAvailabilityCreatedEvent(freelancerId, availabilityDate, fromTime, toTime))
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

    // event handlers

    /**
     * Event handler for create
     */
    private fun applyEvent(event: FreelancerAvailabilityCreatedEvent) {
        val availability = Availability(event.availabilityDate, event.fromTime, event.toTime)
        availabilities.add(availability)
    }

    /**
     * Event handler for delete
     */
    private fun applyEvent(event: FreelancerAvailabilityDeletedEvent) {
        availabilities.removeIf { it.availabilityDate == event.availabilityDate }
    }

    /**
     *
     */
    override fun toString(): String {
        return "Freelancer(id=$freelancerId, firstName=$firstName, lastName=$lastName, role=$role"
    }
}
