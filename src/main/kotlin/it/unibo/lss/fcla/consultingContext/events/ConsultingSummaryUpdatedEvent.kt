package it.unibo.lss.fcla.consultingContext.events

import it.unibo.lss.fcla.consultingContext.consulting.ConsultingId
import it.unibo.lss.fcla.consultingContext.consulting.Date
import it.unibo.lss.fcla.consultingContext.interfaces.DomainEvent

/**
 * @author Stefano Braggion
 *
 * Event representing an updated consulting summary
 */
data class ConsultingSummaryUpdatedEvent(
    val consultingId: ConsultingId,
    val consultingType: String,
    val consultingDate: Date
) : DomainEvent