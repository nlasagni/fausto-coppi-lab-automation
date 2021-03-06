package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryTypeCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

typealias ConsultingId = String

/**
 * @author Stefano Braggion
 *
 * Representing a consulting
 * TODO da tenere consulting summary???
 */
class Consulting(
    val consultingId: ConsultingId,
    val freelancerId: FreelancerId
) : AbstractAggregate(consultingId) {

    private lateinit var consultingSummary: ConsultingSummary


    companion object {
        fun createConsulting(consultingId: ConsultingId, freelancerId: FreelancerId) : Consulting {

            return Consulting(consultingId, freelancerId)
        }

        fun hydrateConsulting(aggregateId: AggregateId, freelancerId: FreelancerId, eventList: List<DomainEvent>) : Consulting {
            var consulting = Consulting.createConsulting(aggregateId, freelancerId)
            eventList.forEach { consulting.applyEvent(it) }

            return consulting
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
     * TODO add validation for data
     */
    private fun apply(event: ConsultingSummaryCreatedEvent) {
        if (event.description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeEmpty()
        if (event.consultingType.isEmpty()) throw ConsultingSummaryTypeCannotBeEmpty()

        consultingSummary = ConsultingSummary(event.consultingType, event.description, event.consultingDate)
    }

    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}
