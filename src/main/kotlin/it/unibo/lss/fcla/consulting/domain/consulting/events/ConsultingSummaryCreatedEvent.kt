package it.unibo.lss.fcla.consulting.domain.consulting.events

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 * Event representing a created consulting summary
 */
data class ConsultingSummaryCreatedEvent(
    val freelancerId: FreelancerId,
    val consultingDate: Date,
    val consultingType: ConsultingType,
    val consultingDescription: String
) : DomainEvent
