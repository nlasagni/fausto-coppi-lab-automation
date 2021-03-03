package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryTypeCannotBeNull
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

typealias ConsultingId = String

/**
 * @author Stefano Braggion
 *
 * Representing a consulting
 */
class Consulting(
    val consultingId: ConsultingId
) : AbstractAggregate(consultingId) {

    private lateinit var consultingSummary: ConsultingSummary

    constructor(
        consultingId: ConsultingId,
        consultingType: String,
        description: String,
        consultingDate: Date,
        freelancerId: FreelancerId
    ) :
        this(consultingId) {
            this.raiseEvent(
                ConsultingSummaryCreatedEvent(
                    consultingId,
                    consultingType,
                    consultingDate,
                    description,
                    freelancerId
                )
            )
        }

    init {

    }

    /**
     * Raise the event for updating the description
     */
    fun updateDescription(newDescription: String) {
        if (newDescription.isEmpty()) throw ConsultingSummaryDescriptionCannotBeNull()

        raiseEvent(ConsultingSummaryUpdatedDescriptionEvent(consultingId, newDescription))
    }

    /**
     * Apply the event: updated the description of the consulting summary
     */
    private fun apply(event: ConsultingSummaryUpdatedDescriptionEvent) {
        consultingSummary = ConsultingSummary(
            consultingSummary.consultingType,
            event.description,
            consultingSummary.consultingDate
        )
    }

    /**
     * Apply the event: created a new consulting summary
     */
    private fun apply(event: ConsultingSummaryCreatedEvent) {
        if (event.description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeNull()
        if (event.consultingType.isEmpty()) throw ConsultingSummaryTypeCannotBeNull()

        consultingSummary = ConsultingSummary(event.consultingType, event.description, event.consultingDate)
    }

    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}
