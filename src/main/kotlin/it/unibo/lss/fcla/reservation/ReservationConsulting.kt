package it.unibo.lss.fcla.reservation

import java.util.Date

class ReservationConsulting(
    override val date: Date,
    override val member: Member,
    val professional: Professional,
    val name: Consulting
) : Reservation(date, member) {

    override fun updateDate(newDate: Date): ReservationConsulting {
        return ReservationConsulting(newDate, member, professional, name)
    }

    override fun toString(): String = "Reservation consulting $name with $professional"

    override fun equals(other: Any?): Boolean {
        return if (other == null && other !is ReservationConsulting) false else other.hashCode() == hashCode()
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + member.hashCode()
        result = 31 * result + professional.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
