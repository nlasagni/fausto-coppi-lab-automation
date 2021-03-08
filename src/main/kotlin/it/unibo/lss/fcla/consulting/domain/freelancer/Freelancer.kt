package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.*
import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerAvailabilityCreatedEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerAvailabilityDeletedEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerCreatedEvent
import java.time.LocalTime

typealias FreelancerId = String

/**
 * @author Stefano Braggion
 *
 * Represents a freelancer
 */
class Freelancer private constructor(
    val freelancerId: FreelancerId
) : AbstractAggregate(freelancerId) {

    private val availabilities = mutableListOf<Availability>()
    private lateinit var personalData: FreelancerPersonalData

    init {
        if(freelancerId.isEmpty()) {
            throw FreelancerMustHaveAValidId()
        }
    }

    companion object {
        /**
         *
         */
        fun createFreelancer(freelancerId: FreelancerId, firstName: String, lastName: String, role: FreelancerRole) : Freelancer {
            //return Freelancer(freelancerId, firstName, lastName, role)
            val freelancer = Freelancer(freelancerId)
            freelancer.raiseEvent(FreelancerCreatedEvent(freelancerId, firstName, lastName, role))

            return freelancer
        }

        /**
         *
         */
        fun rehydrateFreelancer(aggregateId: AggregateId, eventList: List<DomainEvent>) : Freelancer {
            val freelancer = Freelancer(aggregateId)
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
    private fun apply(event: FreelancerCreatedEvent) {
        personalData = FreelancerPersonalData(event.firstName, event.lastName, event.role)
    }

    /**
     *
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is FreelancerAvailabilityCreatedEvent -> apply(event)
            is FreelancerAvailabilityDeletedEvent -> apply(event)
            is FreelancerCreatedEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }

    /**
     *
     */
    override fun toString(): String {
        return "Freelancer(id=$freelancerId, firstName=${personalData.firstName}, " +
                "lastName=${personalData.lastName}, role=${personalData.role.toString()}"
    }
}
