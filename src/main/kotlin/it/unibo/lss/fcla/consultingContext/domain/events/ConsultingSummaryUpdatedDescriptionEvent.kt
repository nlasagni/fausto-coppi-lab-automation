package it.unibo.lss.fcla.consultingContext.domain.events

import it.unibo.lss.fcla.consultingContext.consulting.ConsultingId
import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * Event representing an updated consulting summary
 */
data class ConsultingSummaryUpdatedDescriptionEvent(
    val consultingId: ConsultingId,
    val description: String
) : DomainEvent