package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityAlreadyExist
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityDoesNotExist
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerAvailabilityNotValidTime
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerMustHaveAValidId
import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerAvailabilityCreatedEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerAvailabilityDeletedEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerCreatedEvent
import java.time.LocalDate
import java.time.LocalTime

typealias FreelancerId = String

/**
 * @author Stefano Braggion
 *
 * Main entity representing a freelancer. It contains all the behaviour for freelancers in order to
 * create a new day availability, update an existing one and also delete it.
 *
 *
 */
class Freelancer internal constructor(
    val freelancerId: FreelancerId
) : AbstractAggregate(freelancerId) {

    private val availabilities = mutableListOf<Availability>()
    private lateinit var personalData: FreelancerPersonalData

    /**
     * Check invariants
     */
    init {
        if (freelancerId.isEmpty()) {
            throw FreelancerMustHaveAValidId()
        }
    }

    companion object {
        /**
         * Method used to restore the current state of a freelancer, applying all the events
         * occurred and stored in the event store
         */
        fun rehydrateFreelancer(aggregateId: AggregateId, eventList: List<DomainEvent>): Freelancer {
            val freelancer = Freelancer(aggregateId)
            eventList.forEach { freelancer.applyEvent(it) }

            return freelancer
        }
    }

    /**
     * Retrieve the first name of the freelancer
     */
    fun getFirstName() = personalData.firstName

    /**
     * Retrieve the last name of the freelancer
     */
    fun getLastName() = personalData.lastName

    /**
     * Retrieve the role of the freelancer
     */
    fun getRole() = personalData.role

    /**
     * Add an availability day and hours with given [newAvailabilityDate], [fromTime], [toTime]
     */
    fun addAvailability(newAvailabilityDate: LocalDate, fromTime: LocalTime, toTime: LocalTime) {
        if (!fromTime.isBefore(toTime)) throw FreelancerAvailabilityNotValidTime()

        val exist = availabilities.firstOrNull { it.availabilityDate == newAvailabilityDate } != null
        if (exist) throw FreelancerAvailabilityAlreadyExist()

        raiseEvent(FreelancerAvailabilityCreatedEvent(freelancerId, newAvailabilityDate, fromTime, toTime))
    }

    /**
     * Update an existing availability for the given [availabilityDate] with new [fromTime] and [toTime]
     */
    fun updateAvailability(availabilityDate: LocalDate, fromTime: LocalTime, toTime: LocalTime) {
        if (!fromTime.isBefore(toTime)) throw FreelancerAvailabilityNotValidTime()

        val exist = availabilities.firstOrNull { it.availabilityDate == availabilityDate } != null
        if (!exist) throw FreelancerAvailabilityDoesNotExist()

        raiseEvent(FreelancerAvailabilityDeletedEvent(freelancerId, availabilityDate))
        raiseEvent(FreelancerAvailabilityCreatedEvent(freelancerId, availabilityDate, fromTime, toTime))
    }

    /**
     * Delete the availability for the given [availabilityDate]
     */
    fun deleteAvailability(availabilityDate: LocalDate) {
        raiseEvent(FreelancerAvailabilityDeletedEvent(freelancerId, availabilityDate))
    }

    /**
     * Retrieve the freelancer availabilities for the given [date]
     */
    fun getAvailabilityForDay(availabilityDate: LocalDate): AvailabilityHours {
        val fromTime = availabilities.firstOrNull { it.availabilityDate == availabilityDate }?.fromTime
        val toTime = availabilities.firstOrNull { it.availabilityDate == availabilityDate }?.toTime

        if (fromTime == null || toTime == null) {
            throw FreelancerAvailabilityDoesNotExist()
        }

        return AvailabilityHours(fromTime, toTime)
    }

    // event handlers

    /**
     * Apply the event [FreelancerAvailabilityCreatedEvent]: created a new freelancer availability for a day
     */
    private fun apply(event: FreelancerAvailabilityCreatedEvent) {
        val availability = Availability(event.availabilityDate, event.fromTime, event.toTime)
        availabilities.add(availability)
    }

    /**
     * Apply the event [FreelancerAvailabilityDeletedEvent]: delete an existing freelancer availability for a day
     */
    private fun apply(event: FreelancerAvailabilityDeletedEvent) {
        availabilities.removeIf { it.availabilityDate == event.availabilityDate }
    }

    /**
     * Apply the event [FreelancerCreatedEvent]: created a new freelancer
     */
    private fun apply(event: FreelancerCreatedEvent) {
        personalData = FreelancerPersonalData(event.firstName, event.lastName, event.role)
    }

    /**
     * Select which [event] to apply
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is FreelancerAvailabilityCreatedEvent -> apply(event)
            is FreelancerAvailabilityDeletedEvent -> apply(event)
            is FreelancerCreatedEvent -> apply(event)
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * String representation of a freelancer
     */
    override fun toString(): String {
        return "Freelancer(id=$freelancerId, firstName=${personalData.firstName}, " +
            "lastName=${personalData.lastName}, role=${personalData.role}"
    }
}
