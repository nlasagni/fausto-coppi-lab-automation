package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingCreatedEvent
import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingSummaryUpdatedDescriptionEvent
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidId
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidMember

typealias ConsultingId = String
typealias MemberId = String

/**
 * @author Stefano Braggion
 *
 * The [Consulting] is the entity that contains the behaviours in order to create a consulting, update the
 * related description of the summary and retrieving the information about that
 */
class Consulting internal constructor(
    private val consultingId: ConsultingId
) : AbstractAggregate(consultingId) {

    private lateinit var consultingSummary: ConsultingSummary
    private lateinit var memberId: MemberId

    /**
     * Check invariants
     */
    init {
        if (consultingId.isEmpty()) {
            throw ConsultingMustHaveAValidId()
        }
    }

    /**
     * Retrieve the member Id of the consulting
     */
    fun getMemberId(): MemberId = memberId

    companion object {
        /**
         * Method used to restore the current state of a consulting, applying all the events
         * occurred and stored in the event store
         */
        fun rehydrateConsulting(
            aggregateId: AggregateId,
            eventList: List<DomainEvent>
        ): Consulting {

            val consulting = Consulting(aggregateId)
            eventList.forEach { consulting.applyEvent(it) }

            return consulting
        }
    }

    /**
     * Retrieve the consulting summary
     */
    fun getConsultingSummary(): ConsultingSummary = consultingSummary.copy()

    /**
     * Update the summary description of a consulting replacing the existent
     * with [consultingDescription]
     */
    fun updateSummaryDescription(consultingDescription: String) {
        raiseEvent(ConsultingSummaryUpdatedDescriptionEvent(consultingId, consultingDescription))
    }

    /**
     * Retrieve the current description of the summary
     */
    fun getSummaryDescription(): String = consultingSummary.description

    /**
     * Apply the event [ConsultingCreatedEvent]: created a new consulting with
     * a consulting summary
     */
    private fun apply(event: ConsultingCreatedEvent) {
        if (event.memberId.isEmpty()) {
            throw ConsultingMustHaveAValidMember()
        }
        memberId = event.memberId
        consultingSummary = ConsultingSummary(
            event.consultingDate,
            event.freelancerId,
            event.consultingType,
            event.description
        )
    }

    /**
     * Apply the event [ConsultingSummaryUpdatedDescriptionEvent]: the description
     * of the summary was updated
     */
    private fun apply(event: ConsultingSummaryUpdatedDescriptionEvent) {
        consultingSummary = ConsultingSummary(
            consultingSummary.consultingDate,
            consultingSummary.freelancerId,
            consultingSummary.consultingType,
            event.description
        )
    }

    /**
     * Select which [event] to apply
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is ConsultingCreatedEvent -> apply(event)
            is ConsultingSummaryUpdatedDescriptionEvent -> apply(event)
            else -> throw IllegalArgumentException()
        }
    }
}
