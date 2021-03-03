package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryTypeCannotBeEmpty
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

    companion object {
        fun createConsulting(consultingId: ConsultingId, consultingType: String,
            description: String, consultingDate: Date, freelancerId: FreelancerId) : Consulting {

            return Consulting(consultingId, consultingType, description, consultingDate, freelancerId)
        }
    }

    init {

    }

    /**
     * Raise the event for updating the description
     */
    fun updateDescription(newDescription: String) {
        if (newDescription.isEmpty()) throw ConsultingSummaryDescriptionCannotBeEmpty()

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
        if (event.description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeEmpty()
        if (event.consultingType.isEmpty()) throw ConsultingSummaryTypeCannotBeEmpty()

        consultingSummary = ConsultingSummary(event.consultingType, event.description, event.consultingDate)
    }

    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            is ConsultingSummaryCreatedEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}
