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