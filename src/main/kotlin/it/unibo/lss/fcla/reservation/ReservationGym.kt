package it.unibo.lss.fcla.reservation

import java.util.Date

class ReservationGym(
        override val date: Date,
        override val member: Member,
        val machine: Machine) : Reservation(date, member) {

    override fun updateDate(newDate: Date): Reservation {
        return ReservationGym(newDate,member,machine)
    }

    override fun toString(): String =
            "This reservation is for $machine the $date for $member"
}
