package it.unibo.lss.fcla.consulting.domain.member

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingSummary
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 */
data class MemberReceivedConsultingEvent(
    val memberId: MemberId,
    val consultingId: ConsultingId,
    val consultingSummary: ConsultingSummary
) : DomainEvent