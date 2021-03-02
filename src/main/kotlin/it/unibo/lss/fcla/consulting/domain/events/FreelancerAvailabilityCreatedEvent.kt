package it.unibo.lss.fcla.consulting.domain.events

import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.freelancer.FreelancerId
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
) : DomainEvent
