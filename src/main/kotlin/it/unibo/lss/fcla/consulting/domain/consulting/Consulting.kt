package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingCreatedEvent
import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingSummaryCreatedEvent
import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingSummaryUpdatedDescriptionEvent
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidId
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidMember
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

typealias ConsultingId = String
typealias MemberId = String

/**
 * @author Stefano Braggion
 *
 * The [Consulting] represent the
 *
 *
 */
class Consulting internal constructor(
    private val consultingId: ConsultingId,
    private val memberId: MemberId
) : AbstractAggregate(consultingId) {

    private lateinit var consultingSummary: ConsultingSummary

    init {
        if(consultingId.isEmpty()) {
            throw ConsultingMustHaveAValidId()
        }

        if(memberId.isEmpty()) {
            throw ConsultingMustHaveAValidMember()
        }
    }

    companion object {
        fun rehydrateConsulting(aggregateId: AggregateId, memberId: MemberId, eventList: List<DomainEvent>) : Consulting {
            val consulting = Consulting(aggregateId, memberId)
            eventList.forEach { consulting.applyEvent(it) }

            return consulting
        }
    }

    fun updateSummaryDescription(consultingDescription: String) {
        raiseEvent(ConsultingSummaryUpdatedDescriptionEvent(consultingId, consultingDescription))
    }

    /**
     *
     */
    fun getSummaryDescription() : String = consultingSummary.description

    /**
     * Apply the event [ConsultingCreatedEvent]: created a new consulting with
     * a consulting summary
     */
    private fun apply(event: ConsultingCreatedEvent) {
        consultingSummary = ConsultingSummary(event.consultingDate, event.freelancerId,
        event.consultingType, event.description)
    }

    /**
     * Apply the event [ConsultingSummaryUpdatedDescriptionEvent]: the description
     * of the summary was updated
     */
    private fun apply(event: ConsultingSummaryUpdatedDescriptionEvent) {
        consultingSummary = ConsultingSummary(consultingSummary.consultingDate,
            consultingSummary.freelancerId, consultingSummary.consultingType, event.description)
    }

    /**
     * Select which event to apply
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingCreatedEvent -> apply(event)
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}
