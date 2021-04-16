/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId

/**
 * Domain service that updates the profile of a member.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface MemberProfileUpdater {
    /**
     * TODO In future prototypes, introduce the concept of Profile at the domain level.
     *
     * Updates the profile of the member referred by the specifiec [memberId].
     */
    fun updateProfile(memberId: MemberId)
}
