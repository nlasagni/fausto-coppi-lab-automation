package it.unibo.lss.fcla.reservation

import java.util.Date

class ReservationGym(
    override val date: Date,
    override val member: Member,
    val machine: Machine?
) : Reservation(date, member) {

    override fun updateDate(newDate: Date): ReservationGym {
        return ReservationGym(newDate, member, machine)
    }

    override fun toString(): String = "This reservation is for $machine the $date for $member"

    override fun equals(other: Any?): Boolean {
        return if (other == null && other !is ReservationGym) false else other.hashCode() == hashCode()
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + member.hashCode()
        result = 31 * result + (machine?.hashCode() ?: 0)
        return result
    }
}
