package it.unibo.lss.fcla.consulting.domainevents

import it.unibo.lss.fcla.consulting.models.ConsultingId
import it.unibo.lss.fcla.consulting.models.Date

/**
 * @author Stefano Braggion
 *
 * Event representing a created consulting summary
 */
data class ConsultingSummaryCreatedEvent(
    val consultingId: ConsultingId,
    val consultingType: String,
    val consultingDate: Date
)