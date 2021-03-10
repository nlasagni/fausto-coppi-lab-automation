package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import java.util.UUID

/**
 * Projection used to update the [Member] given its events
 */
class MemberProjection(override val init: Member) : Projection<Member> {

    constructor(memberName: String, memberSurname: String, memberId: UUID) : this(
        Member(
            memberName,
            memberSurname,
            memberId
        )
    )

    /**
     * Return an updated [Member] based on the given event.
     */
    override fun update(state: Member, event: Event): Member = when (event) {
        is MemberAddConsultingReservation -> state.addConsultingReservation(event.reservation)
        is MemberAddWorkoutReservation -> state.addWorkoutReservation(event.reservation)
        is MemberDeleteConsultingReservation -> state.deleteConsultingReservation(event.reservation)
        is MemberDeleteWorkoutReservation -> state.deleteWorkoutReservation(event.reservation)
        else -> state
    }
}
