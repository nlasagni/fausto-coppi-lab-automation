package it.unibo.lss.fcla.reservation.domain.entities.member

import it.unibo.lss.fcla.reservation.common.MembershipCard

/**
 * Class used to identify a member
 *
 * @param firstName The name of the member
 * @param lastName The surname of the member
 * @param id The code of member's card
 */
class Member(val firstName: String, val lastName: String, val id: MembershipCard) {
    private val memberRequestedWorkoutReservation = MemberRequestedWorkoutReservation()
    private val memberRequestedConsultingReservation = MemberRequestedConsultingReservation()

    init {
        // possible checks
    }
}
