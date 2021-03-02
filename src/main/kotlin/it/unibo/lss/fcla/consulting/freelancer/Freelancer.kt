package it.unibo.lss.fcla.consulting.freelancer

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.consulting.Date
import it.unibo.lss.fcla.consulting.domain.events.FreelancerAvailabilityCreatedEvent
import it.unibo.lss.fcla.consulting.domain.events.FreelancerAvailabilityDeletedEvent
import it.unibo.lss.fcla.consulting.domain.events.FreelancerCreatedEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityAlreadyExist
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityNotValidTime
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents a freelancer
 */
class Freelancer(
    val freelancerId: FreelancerId
) : AbstractAggregate() {

    private val availabilities = mutableListOf<Availability>()
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var role: FreelancerRole

    constructor (freelancerId: FreelancerId, firstName: String, lastName: String, role: FreelancerRole) :
        this(freelancerId) {
            raiseEvent(FreelancerCreatedEvent(freelancerId, firstName, lastName, role))
        }

    init {
        // register event handlers
        this.register<FreelancerAvailabilityCreatedEvent>(this::applyEvent)
        this.register<FreelancerAvailabilityDeletedEvent>(this::applyEvent)
        this.register<FreelancerCreatedEvent>(this::applyEvent)
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
        if (exist) throw FreelancerAvailabilityAlreadyExist()

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

    private fun applyEvent(event: FreelancerCreatedEvent) {
        firstName = event.firstName
        lastName = event.lastName
        role = event.role
    }

    /**
     *
     */
    override fun toString(): String {
        return "Freelancer(id=$freelancerId, firstName=$firstName, lastName=$lastName, role=$role"
    }
}
