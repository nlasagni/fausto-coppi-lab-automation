package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import java.util.UUID

class MemberProjection(val init: Member) {

    constructor(memberName: String, memberSurname: String, memberId: UUID) : this(
        Member(
            memberName,
            memberSurname,
            memberId
        )
    )

    fun update(member: Member, event: Event): Member = when (event) {
        is MemberAddConsultingReservationEvent -> member.addConsultingReservation(event.reservation)
        is MemberAddWorkoutReservationEvent -> member.addWorkoutReservation(event.reservation)
        is MemberDeleteConsultingReservationEvent -> member.deleteConsultingReservation(event.reservation)
        is MemberDeleteWorkoutReservationEvent -> member.deleteWorkoutReservation(event.reservation)
        else -> member
    }
}
