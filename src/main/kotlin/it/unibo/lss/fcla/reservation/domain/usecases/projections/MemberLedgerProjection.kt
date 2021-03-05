package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import java.util.UUID

/**
 * Projection used to update the [MemberLedger] given its events
 */
class MemberLedgerProjection(override val init: MemberLedger) : Projection<MemberLedger> {

    constructor(ledgerId: UUID) : this(MemberLedger(ledgerId))

    /**
     * Return an updated [MemberLedger] based on the given event.
     */
    override fun update(state: MemberLedger, event: Event): MemberLedger = when (event) {
        is LedgerAddMemberEvent -> state.addMemberToLedger(event.member)
        else -> state
    }
}
