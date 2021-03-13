package it.unibo.lss.fcla.consulting.domain.consulting.events

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 * Event representing an updated consulting summary description
 */
data class ConsultingSummaryUpdatedDescriptionEvent(
    val consultingId: ConsultingId,
    val description: String
) : DomainEvent
