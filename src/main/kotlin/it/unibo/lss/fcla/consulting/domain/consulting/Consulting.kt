package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
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
class Consulting(
    private val consultingId: ConsultingId,
    private val memberId: MemberId,
    private val consultingDate: Date,
    private val freelancerId: FreelancerId,
    private val consultingType: ConsultingType,
    private val description: String
) : AbstractAggregate(consultingId) {

    private lateinit var consultingSummary: ConsultingSummary

    /**
     *
     */
    init {
        if(consultingId.isEmpty()) {
            throw ConsultingMustHaveAValidId()
        }
        if(memberId.isEmpty()) {
            throw ConsultingMustHaveAValidMember()
        }

        raiseEvent(ConsultingSummaryCreatedEvent(freelancerId, consultingDate, consultingType, description))
    }

    companion object {
        /**
         *
         */
        fun createConsulting(consultingId: ConsultingId, memberId: MemberId,
        consultingDate: Date, freelancerId: FreelancerId, consultingType: ConsultingType,
        description: String) : Consulting {
            return Consulting(consultingId, memberId, consultingDate, freelancerId, consultingType, description)
        }

        /*fun hydrateConsulting(aggregateId: AggregateId, memberId: MemberId,
                              eventList: List<DomainEvent>) : Consulting {
            var consulting = Consulting.createConsulting(aggregateId, memberId)
            eventList.forEach { consulting.applyEvent(it) }

            return consulting
        }*/
    }

    fun updateSummaryDescription(consultingDescription: String) {
        raiseEvent(ConsultingSummaryUpdatedDescriptionEvent(consultingId, consultingDescription))
    }

    /**
     *
     */
    fun createConsultingSummary(freelancerId: FreelancerId, consultingDate: Date,
    consultingType: ConsultingType, consultingDescription: String) {
        raiseEvent(ConsultingSummaryCreatedEvent(freelancerId, consultingDate, consultingType, consultingDescription))
    }

    /**
     * Apply the event: created a new consulting summary
     */
    private fun apply(event: ConsultingSummaryCreatedEvent) {
        consultingSummary = ConsultingSummary(event.consultingDate, event.freelancerId,
        event.consultingType, event.consultingDescription)
    }

    private fun apply(event: ConsultingSummaryUpdatedDescriptionEvent) {
        consultingSummary = ConsultingSummary(consultingDate, freelancerId, consultingType, event.description)
    }

    /**
     *
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingSummaryCreatedEvent -> apply(event)
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}
