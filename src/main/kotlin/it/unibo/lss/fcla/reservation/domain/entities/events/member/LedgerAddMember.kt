package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import java.util.UUID

/**
 * An event representing an added member to the Ledger
 */
data class LedgerAddMember(
    override val eventId: UUID,
    val member: Member
) : Event
