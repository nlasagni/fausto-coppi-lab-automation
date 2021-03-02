package it.unibo.lss.fcla.consulting.domain.member

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingSummary
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
) {

    private val memberConsultings: HashMap<ConsultingId, ConsultingSummary> = hashMapOf()

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
        memberConsultings.put(consultingId, consultingSummary)
    }
}