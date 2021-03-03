package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

data class FreelancerAvailabilityDeletedEvent(
    val freelancerId: FreelancerId,
    val availabilityDate: Date
) : DomainEvent