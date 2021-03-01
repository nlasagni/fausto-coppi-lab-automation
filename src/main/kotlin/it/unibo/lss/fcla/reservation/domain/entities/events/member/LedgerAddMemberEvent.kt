package it.unibo.lss.fcla.reservation.domain.entities.events.member

import it.unibo.lss.fcla.reservation.common.Event
import java.util.UUID

/**
 * [Event] representing an added member to the Ledger
 */
data class LedgerAddMemberEvent(
    override val id: UUID,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Event
