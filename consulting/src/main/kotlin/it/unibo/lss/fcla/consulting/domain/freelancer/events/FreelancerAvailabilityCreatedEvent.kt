package it.unibo.lss.fcla.consulting.domain.freelancer.events

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Event representing a created freelancer availability
 */
data class FreelancerAvailabilityCreatedEvent(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : DomainEvent
