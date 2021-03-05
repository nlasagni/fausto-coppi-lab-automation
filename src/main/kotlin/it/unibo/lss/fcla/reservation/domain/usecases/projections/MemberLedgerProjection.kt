package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import java.util.UUID

class MemberLedgerProjection(override val init: MemberLedger) : Projection<MemberLedger> {

    constructor(ledgerId: UUID) : this(MemberLedger(ledgerId))

    override fun update(state: MemberLedger, event: Event): MemberLedger = when (event) {
        is LedgerAddMemberEvent -> state.addMemberToLedger(event.member)
        else -> state
    }
}
