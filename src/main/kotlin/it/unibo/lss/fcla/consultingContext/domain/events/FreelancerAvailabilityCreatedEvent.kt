package it.unibo.lss.fcla.consultingContext.domain.events

import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerId
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 *
 */
data class FreelancerAvailabilityCreatedEvent(
    val freelancerId: FreelancerId,
    val availabilityDate: Date,
    val fromTime: LocalTime,
    val toTime: LocalTime
): DomainEvent
