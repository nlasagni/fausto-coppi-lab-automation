package it.unibo.lss.fcla.consulting.domain.member

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingSummary
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberConsultingAlreadyExist
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberLastNameCannotBeNull

typealias MemberId = String

/**
 * @author Stefano Braggion
 *
 *
 */
class Member(
    val memberId: MemberId
) : AbstractAggregate(memberId) {

    private val memberConsultings: HashMap<ConsultingId, ConsultingSummary> = hashMapOf()
    private lateinit var firstName: String
    private lateinit var lastName: String

    companion object {
        fun createMember(memberId: MemberId, firstName: String, lastName: String) : Member {
            return Member(memberId, firstName, lastName)
        }

        fun hydrateMember(aggregateId: AggregateId, eventList: List<DomainEvent>) : Member {
            var member = Member(aggregateId)
            eventList.forEach { member.applyEvent(it) }

            return member
        }
    }

    constructor(memberId: MemberId, firstName: String, lastName: String) : this(memberId) {
        raiseEvent(MemberCreatedEvent(memberId, firstName, lastName))
    }

    /**
     *
     */
    fun receiveConsulting(consultingId: ConsultingId, consultingSummary: ConsultingSummary) {
        if(memberConsultings.containsKey(consultingId))
            throw MemberConsultingAlreadyExist()
        raiseEvent(MemberReceivedConsultingEvent(memberId, consultingId, consultingSummary))
    }

    // events handler

    /**
     * Event handler for manage [event] of type [MemberReceivedConsultingEvent]
     */
    private fun apply(event: MemberReceivedConsultingEvent) {
        memberConsultings[event.consultingId] = event.consultingSummary
    }

    /**
     * Event handler for manage [event] of type [MemberCreatedEvent]
     */
    private fun apply(event: MemberCreatedEvent) {
        if(firstName.isEmpty())
            throw MemberFirstNameCannotBeNull()
        if(lastName.isEmpty())
            throw MemberLastNameCannotBeNull()

        firstName = event.firstName
        lastName = event.lastName
    }

    /**
     *
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is MemberReceivedConsultingEvent -> apply(event)
            is MemberCreatedEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}