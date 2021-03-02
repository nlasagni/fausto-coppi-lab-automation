package it.unibo.lss.fcla.consulting.domain.events

import it.unibo.lss.fcla.consulting.consulting.Date
import it.unibo.lss.fcla.consulting.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.freelancer.FreelancerId

data class FreelancerAvailabilityDeletedEvent(
    val freelancerId: FreelancerId,
    val availabilityDate: Date
) : DomainEvent