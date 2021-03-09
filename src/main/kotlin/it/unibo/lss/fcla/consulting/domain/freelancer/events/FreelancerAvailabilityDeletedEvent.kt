package it.unibo.lss.fcla.consulting.domain.freelancer.events

import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 * Event representing a deleted freelancer availability
 */
data class FreelancerAvailabilityDeletedEvent(
    val freelancerId: FreelancerId,
    val availabilityDate: Date
) : DomainEvent
