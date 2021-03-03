package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import java.util.UUID

class MemberLedgerProjection(val init: MemberLedger) {

    constructor(ledgerId: UUID) : this(MemberLedger(ledgerId))

    fun update(ledger: MemberLedger, event: Event): MemberLedger = when (event) {
        is LedgerAddMemberEvent -> ledger.addMemberToLedger(event.member)
        else -> ledger
    }
}
