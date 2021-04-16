/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.infrastructure.service

import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater

/**
 * Dummy implementation of the [MemberProfileUpdater] service.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class MemberProfileUpdaterImpl : MemberProfileUpdater {
    override fun updateProfile(memberId: MemberId) {
        // TODO Add implementation
    }
}
