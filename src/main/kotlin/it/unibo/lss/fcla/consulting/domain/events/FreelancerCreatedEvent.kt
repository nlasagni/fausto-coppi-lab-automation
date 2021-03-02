package it.unibo.lss.fcla.consulting.domain.events

import it.unibo.lss.fcla.consulting.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.freelancer.FreelancerRole

data class FreelancerCreatedEvent(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : DomainEvent
