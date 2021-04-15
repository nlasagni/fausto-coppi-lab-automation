/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMember
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Projection used to update the [MemberLedger] given its events
 */
class MemberLedgerProjection(override val init: MemberLedger) : Projection<MemberLedger> {

    constructor(ledgerId: UUID) : this(MemberLedger(ledgerId))

    /**
     * Return an updated [MemberLedger] based on the given event.
     */
    override fun update(state: MemberLedger, event: Event): MemberLedger = when (event) {
        is LedgerAddMember -> state.addMemberToLedger(event.member)
        else -> state
    }
}
