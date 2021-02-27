package it.unibo.lss.fcla.consultingContext.domain.events

import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerId
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerRole

data class FreelancerCreatedEvent(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : DomainEvent
