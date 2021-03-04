package it.unibo.lss.fcla.consulting.domain.member

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *
 */
data class MemberCreatedEvent(
    val memberId: MemberId,
    val firstName: String,
    val lastName: String
) : DomainEvent