package it.unibo.lss.fcla.reservation

import java.util.*

class Desk {

    val reservation: MutableList<Reservation> = mutableListOf()

    fun createGymRes(date: Date, member: Member, machine: Machine?) {
        val res = ReservationGym(date,member,machine)
        reservation.add(res)
    }

    fun createConsRes(date:Date, member: Member, professional: Professional, consulting: Consulting) {
        val res = ReservationConsulting(date,member,professional,consulting)
        reservation.add(res)
    }

    fun read(member: Member?, date: Date?) {
        TODO()
    }

    fun update(reservation: Reservation, date: Date) {
        TODO()
    }

    fun delete(reservation: Reservation) {
        TODO()
    }
}
