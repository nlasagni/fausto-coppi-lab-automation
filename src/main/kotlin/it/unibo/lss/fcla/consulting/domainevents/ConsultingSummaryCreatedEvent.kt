package it.unibo.lss.fcla.consulting.domainevents

import it.unibo.lss.fcla.consulting.models.ConsultingId

data class ConsultingSummaryCreatedEvent(
    val consultingId: ConsultingId
)