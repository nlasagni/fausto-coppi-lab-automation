package it.unibo.lss.fcla.consulting.domain.events

import it.unibo.lss.fcla.consulting.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.consulting.Date
import it.unibo.lss.fcla.consulting.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 * Event representing a created consulting summary
 */
data class ConsultingSummaryCreatedEvent(
    val consultingId: ConsultingId,
    val consultingType: String,
    val consultingDate: Date,
    val description: String,
    val freelancer: FreelancerId
) : DomainEvent
