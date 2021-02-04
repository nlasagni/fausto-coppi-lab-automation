package it.unibo.lss.fcla

import it.unibo.lss.fcla.reservation.Desk
import it.unibo.lss.fcla.reservation.Machine
import it.unibo.lss.fcla.reservation.Member
import it.unibo.lss.fcla.reservation.MembershipCard
import java.util.Calendar

class MainClass {

    companion object MainClass {
        const val YEAR = 2021
        const val MONTH = 2
        const val DAY = 25
    }

    fun basicReservation() {
        val cal = Calendar.getInstance()
        val cardCode = "2025"
        val member = Member(
            "Mario",
            "Rossi",
            MembershipCard(cardCode)
        )
        val machine = Machine("Leg press")
        val desk = Desk()

        cal.set(YEAR, MONTH, DAY)
        desk.createGymReservation(cal.time, member, machine)
        println("Reservation created: ${desk.read()}")
    }
}

fun main() {
    MainClass().basicReservation()
}
