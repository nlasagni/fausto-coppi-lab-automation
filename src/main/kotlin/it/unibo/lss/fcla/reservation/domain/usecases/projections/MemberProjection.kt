package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import java.util.UUID

class MemberProjection(override val init: Member) : Projection<Member> {

    constructor(memberName: String, memberSurname: String, memberId: UUID) : this(
        Member(
            memberName,
            memberSurname,
            memberId
        )
    )

    override fun update(state: Member, event: Event): Member = when (event) {
        is MemberAddConsultingReservationEvent -> state.addConsultingReservation(event.reservation)
        is MemberAddWorkoutReservationEvent -> state.addWorkoutReservation(event.reservation)
        is MemberDeleteConsultingReservationEvent -> state.deleteConsultingReservation(event.reservation)
        is MemberDeleteWorkoutReservationEvent -> state.deleteWorkoutReservation(event.reservation)
        else -> state
    }
}
