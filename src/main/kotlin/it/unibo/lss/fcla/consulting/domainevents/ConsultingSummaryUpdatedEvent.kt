package it.unibo.lss.fcla.consulting.domainevents

import it.unibo.lss.fcla.consulting.models.ConsultingId
import it.unibo.lss.fcla.consulting.models.Date

/**
 * @author Stefano Braggion
 *
 * Event representing an updated consulting summary
 */
data class ConsultingSummaryUpdatedEvent(
    val consultingId: ConsultingId,
    val consultingType: String,
    val consultingDate: Date
)