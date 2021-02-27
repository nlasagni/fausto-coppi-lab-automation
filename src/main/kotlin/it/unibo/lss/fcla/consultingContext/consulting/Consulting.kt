package it.unibo.lss.fcla.consultingContext.consulting

import it.unibo.lss.fcla.consultingContext.common.AbstractAggregate
import it.unibo.lss.fcla.consultingContext.contracts.DomainEvent
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
    val consultingId: ConsultingId
    ): AbstractAggregate() {

    private lateinit var consultingSummary: ConsultingSummary

    constructor(consultingId: ConsultingId, consultingType: String, description: String,
                consultingDate: Date, freelancer: Freelancer) : this(consultingId) {
        this.raiseEvent(ConsultingSummaryCreatedEvent(consultingId, consultingType, consultingDate, description, freelancer))
    }

    init {
        //register all handlers
        this.register<ConsultingSummaryCreatedEvent>(this::applyEvent)
        this.register<ConsultingSummaryUpdatedDescriptionEvent>(this::applyEvent)
    }

    //

    /**
     * Raise the event for updating the description
     */
    fun updateDescription(newDescription: String) {
        if(newDescription.isEmpty())
            throw ConsultingSummaryDescriptionCannotBeNull()

        raiseEvent(ConsultingSummaryUpdatedDescriptionEvent(consultingId, newDescription))
    }


    //event handlers

    /**
     * Apply the event: updated the description of the consulting summary
     */
    private fun applyEvent(event: ConsultingSummaryUpdatedDescriptionEvent) {
        consultingSummary = ConsultingSummary(consultingSummary.consultingType,
            event.description, consultingSummary.consultingDate)
    }

    /**
     * Apply the event: created a new consulting summary
     */
    private fun applyEvent(event: ConsultingSummaryCreatedEvent) {
        if(event.description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeNull()
        if(event.consultingType.isEmpty()) throw ConsultingSummaryTypeCannotBeNull()

        consultingSummary = ConsultingSummary(event.consultingType, event.description, event.consultingDate)
    }

}