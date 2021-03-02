package it.unibo.lss.fcla.consulting.domain.member

import it.unibo.lss.fcla.consulting.common.AbstractAggregate
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingSummary
import it.unibo.lss.fcla.consulting.domain.events.MemberReceivedConsultingEvent
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberConsultingAlreadyExist
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberLastNameCannotBeNull

/**
 * @author Stefano Braggion
 *
 *
 */
class Member(
    val memberId: MemberId,
    val firstName: String,
    val lastName: String
) : AbstractAggregate() {

    private val memberConsultings: HashMap<ConsultingId, ConsultingSummary> = hashMapOf()

    init {
        if(firstName.isEmpty())
            throw MemberFirstNameCannotBeNull()
        if(lastName.isEmpty())
            throw MemberLastNameCannotBeNull()

        this.register<MemberReceivedConsultingEvent>(this::applyEvent)
    }

    /**
     *
     */
    fun receiveConsulting(consultingId: ConsultingId, consultingSummary: ConsultingSummary) {
        if(memberConsultings.containsKey(consultingId))
            throw MemberConsultingAlreadyExist()
        memberConsultings[consultingId] = consultingSummary
    }

    // events handler


}