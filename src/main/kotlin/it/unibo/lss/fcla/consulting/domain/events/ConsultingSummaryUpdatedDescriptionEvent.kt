package it.unibo.lss.fcla.consulting.domain.events

import it.unibo.lss.fcla.consulting.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * Event representing an updated consulting summary
 */
data class ConsultingSummaryUpdatedDescriptionEvent(
    val consultingId: ConsultingId,
    val description: String
) : DomainEvent
