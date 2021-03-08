package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.*
import java.time.LocalTime

typealias FreelancerId = String

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
) : AbstractAggregate(freelancerId) {

    private val availabilities = mutableListOf<Availability>()

    companion object {
        fun createFreelancer(freelancerId: FreelancerId, firstName: String, lastName: String, role: FreelancerRole) : Freelancer {
            return Freelancer(freelancerId, firstName, lastName, role)
        }

        fun hydrateFreelancer(aggregateId: AggregateId, firstName: String, lastName: String, role: FreelancerRole,
                              eventList: List<DomainEvent>) : Freelancer {
            var freelancer = Freelancer(aggregateId, firstName, lastName, role)
            eventList.forEach { freelancer.applyEvent(it) }

            return freelancer
        }
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

    /**
     *
     */
    fun updateAvailability(availabilityDate: Date, fromTime: LocalTime, toTime: LocalTime) {
        if (!fromTime.isBefore(toTime)) throw FreelancerAvailabilityNotValidTime()

        val exist = availabilities.firstOrNull { it.availabilityDate == availabilityDate } != null
        if (!exist) throw FreelancerAvailabilityDoesNotExist()

        raiseEvent(FreelancerAvailabilityDeletedEvent(freelancerId, availabilityDate))
        raiseEvent(FreelancerAvailabilityCreatedEvent(freelancerId, availabilityDate, fromTime, toTime))
    }

    /**
     *
     */
    fun availabilityOfDay(date: Date) = availabilities.firstOrNull { it.availabilityDate == date }

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
    private fun apply(event: FreelancerAvailabilityCreatedEvent) {
        val availability = Availability(event.availabilityDate, event.fromTime, event.toTime)
        availabilities.add(availability)
    }

    /**
     * Event handler for delete
     */
    private fun apply(event: FreelancerAvailabilityDeletedEvent) {
        availabilities.removeIf { it.availabilityDate == event.availabilityDate }
    }


    /**
     *
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is FreelancerAvailabilityCreatedEvent -> apply(event)
            is FreelancerAvailabilityDeletedEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }

    /**
     *
     */
    override fun toString(): String {
        return "Freelancer(id=$freelancerId, firstName=$firstName, lastName=$lastName, role=$role"
    }
}
