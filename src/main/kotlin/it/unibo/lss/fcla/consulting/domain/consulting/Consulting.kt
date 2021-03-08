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
 */
class Consulting(
    val consultingId: ConsultingId,
    val freelancerId: FreelancerId,
    val consultingDate: Date,
    val consultingType: ConsultingType
) : AbstractAggregate(consultingId) {

    private lateinit var consultingSummary: ConsultingSummary

    companion object {
        fun createConsulting(consultingId: ConsultingId, freelancerId: FreelancerId,
                             consultingDate: Date, consultingType: ConsultingType) : Consulting {
            return Consulting(consultingId, freelancerId, consultingDate, consultingType)
        }

        fun hydrateConsulting(aggregateId: AggregateId, freelancerId: FreelancerId,
                              eventList: List<DomainEvent>, consultingType: ConsultingType) : Consulting {
            var consulting = Consulting.createConsulting(aggregateId, freelancerId)
            eventList.forEach { consulting.applyEvent(it) }

            return consulting
        }
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
            event.description
        )
    }

    /**
     * Apply the event: created a new consulting summary
     */
    private fun apply(event: ConsultingSummaryCreatedEvent) {
        if (event.description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeEmpty()

        consultingSummary = ConsultingSummary(event.consultingType, event.description)
    }

    /**
     *
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}
