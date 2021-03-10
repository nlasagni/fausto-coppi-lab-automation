package it.unibo.lss.fcla.consulting.domain.freelancer.events

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole

/**
 * @author Stefano Braggion
 *
 * Event representing a freelancer created
 */
data class FreelancerCreatedEvent(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : DomainEvent
