package it.unibo.lss.fcla.reservation

import java.util.Date

class ReservationConsulting(
    override val date: Date,
    override val member: Member,
    val professional: Professional,
    val name: Consulting
): Reservation(date, member) {

    override fun updateDate(newDate: Date): Reservation {
        return ReservationConsulting(newDate, member, professional, name)
    }

    override fun toString(): String = "Reservation consulting $name with $professional"
}
