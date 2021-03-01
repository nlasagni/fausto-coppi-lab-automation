package it.unibo.lss.fcla

import java.util.Calendar

class MainClass {

    /* companion object MainClass {
        const val YEAR = 2021
        const val DAY = 25
        const val HOUR = 11
        const val MINUTE = 15
        val cal: Calendar = Calendar.getInstance()
        private const val cardCode = "2025"
        val member = Member(
            "Mario",
            "Rossi",
            MembershipCard(cardCode)
        )
        val machine = Machine("Leg press")
        val professional = Professional(
            ProfessionalType.ATHLETIC_TRAINER,
            "Mimmo",
            "Bianchi"
        )
        val desk = Desk()
    }

    fun basicGymReservation() {
        cal.set(YEAR, Calendar.FEBRUARY, DAY, HOUR, MINUTE)
        desk.createGymReservation(cal.time, member, machine)
        println("Reservation created: ${desk.read()}")
    }

    fun basicConsultingReservation() {
        cal.set(YEAR, Calendar.MARCH, DAY, HOUR, MINUTE)
        desk.createConsultingReservation(cal.time, member, professional, Consulting.ATHLETIC_TRAINING)
        println("Reservation created: ${desk.read()}")
    } */
}

fun main() {
    /*
    MainClass().basicGymReservation()
    MainClass().basicConsultingReservation()
    */
    println("Main Class")
}
