package it.unibo.lss.fcla.consultingContext.consulting

import it.unibo.lss.fcla.consultingContext.common.AbstractAggregate
import it.unibo.lss.fcla.consultingContext.domain.events.ConsultingSummaryCreatedEvent
import it.unibo.lss.fcla.consultingContext.domain.events.ConsultingSummaryUpdatedDescriptionEvent
import it.unibo.lss.fcla.consultingContext.exceptions.ConsultingSummaryDescriptionCannotBeNull
import it.unibo.lss.fcla.consultingContext.exceptions.ConsultingSummaryTypeCannotBeNull
import it.unibo.lss.fcla.consultingContext.freelancer.Freelancer

/**
 * @author Stefano Braggion
 *
 * Representing a consulting
 */
class Consulting(
    val consultingId: ConsultingId,
    val consultingType: String,
    val description: String,
    val consultingDate: Date,
    val freelancer: Freelancer
    ): AbstractAggregate() {

    private var consultingSummary: ConsultingSummary

    init {
        if(description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeNull()
        if(consultingType.isEmpty()) throw ConsultingSummaryTypeCannotBeNull()

        consultingSummary = ConsultingSummary(consultingType, description, consultingDate)

        //register all handlers
        this.register<ConsultingSummaryCreatedEvent>(this::applyEvent)
        this.register<ConsultingSummaryUpdatedDescriptionEvent>(this::applyEvent)
    }

    /**
     * Update the description of the summary
     */
    fun updateDescriptionOfConsulting(newDescription: String) {
        if(newDescription.isEmpty())
            throw ConsultingSummaryDescriptionCannotBeNull()

        consultingSummary = ConsultingSummary(consultingSummary.consultingType,
            newDescription, consultingSummary.consultingDate)
    }

}