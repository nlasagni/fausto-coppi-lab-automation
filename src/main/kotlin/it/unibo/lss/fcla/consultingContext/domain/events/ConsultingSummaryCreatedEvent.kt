package it.unibo.lss.fcla.consultingContext.domain.events

import it.unibo.lss.fcla.consultingContext.consulting.ConsultingId
import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent
import it.unibo.lss.fcla.consultingContext.freelancer.FreelancerId

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