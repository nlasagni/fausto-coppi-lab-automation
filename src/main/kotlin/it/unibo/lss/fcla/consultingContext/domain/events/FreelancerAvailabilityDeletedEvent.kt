package it.unibo.lss.fcla.consultingContext.domain.events

import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerId

data class FreelancerAvailabilityDeletedEvent(
    val freelancerId: FreelancerId,
    val availabilityDate: Date
): DomainEvent