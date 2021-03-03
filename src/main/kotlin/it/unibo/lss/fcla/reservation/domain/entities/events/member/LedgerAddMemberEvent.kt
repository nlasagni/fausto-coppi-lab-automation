package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import java.util.UUID

/**
 * [Event] representing an added member to the Ledger
 */
data class LedgerAddMemberEvent(
    override val id: UUID,
    val member: Member
) : Event
