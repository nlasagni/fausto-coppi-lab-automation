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
    val memberId: MemberId,
    val firstName: String,
    val lastName: String
) : AbstractAggregate(memberId) {

    private val memberConsultings: HashMap<ConsultingId, ConsultingSummary> = hashMapOf()

    companion object {
        fun createMember(memberId: MemberId, firstName: String, lastName: String) : Member {
            return Member(memberId, firstName, lastName)
        }

        fun hydrateMember(aggregateId: AggregateId, firstName: String,
                          lastName: String, eventList: List<DomainEvent>) : Member {
            var member = Member(aggregateId, firstName, lastName)
            eventList.forEach { member.applyEvent(it) }

            return member
        }
    }

    init {
        if(firstName.isEmpty())
            throw MemberFirstNameCannotBeNull()
        if(lastName.isEmpty())
            throw MemberLastNameCannotBeNull()
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
     *
     */
    override fun applyEvent(event: DomainEvent) {
        when (event) {
            is MemberReceivedConsultingEvent -> apply(event)
            else -> throw IllegalArgumentException() //TODO fixme
        }
    }
}