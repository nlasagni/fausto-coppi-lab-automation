package it.unibo.lss.fcla.reservation.ui

import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade
import java.util.Calendar
import java.util.Date
import java.util.UUID

class ConsoleUI {

    private val freelancer1 = UUID.randomUUID()
    private val freelancer2 = UUID.randomUUID()
    private val cal = Calendar.getInstance()
    private val date7April: Date
    private val date24April: Date
    private val aim1 = "Injury recovery"
    private val aim2 = "Strengthening"

    init {
        cal.set(2021,Calendar.APRIL,7)
        date7April = cal.time
        cal.set(2021,Calendar.APRIL,24)
        date24April = cal.time
    }

    private fun writeAllCommands() {
        println("List of possible commands\n" +
                "1) Create Consulting Reservation\n" +
                "2) Create Workout Reservation\n" +
                "3) Update Consulting Reservation\n" +
                "4) Update Workout Reservation\n" +
                "5) Delete Consulting Reservation\n" +
                "6) Delete Workout Reservation\n" +
                "7) Consulting Reservation Details\n" +
                "8) Workout Reservation Details\n" +
                "9) Close and exit")
    }

    private fun readInput(max: Int): Int {
        var inputRead = false
        var validInput = 0
        do {
            val str = readLine() ?: ""
            try {
                validInput = str.toInt()
                if(validInput in 1..max) {
                    inputRead = true
                } else {
                    println("Please insert a valid number")
                }
            } catch (exception: NumberFormatException) {
                println("Please insert a valid number")
            }
        } while (! inputRead)
        return validInput
    }

    fun readCommand(): Int {
        writeAllCommands()
        return readInput(9)
    }

    fun readFreelancer(): UUID {
        println("Choose a freelancer\n" +
                "1) Mario Rossi\n" +
                "2) Giovanna Bianchi\n")
        return when(readInput(2)){
            1 -> freelancer1
            else -> freelancer2
        }
    }

    fun readDate(): Date {
        println("Choose a date\n" +
                "1) 2021/4/7\n" +
                "2) 2021/4/24\n")
        return when(readInput(2)){
            1 -> date7April
            else -> date24April
        }
    }

    fun readAim(): String {
        println("Choose an aim\n" +
                "1) Injury recovery\n" +
                "2) Strengthening\n")
        return when(readInput(2)){
            1 -> aim1
            else -> aim2
        }
    }

    fun chooseConsultingReservation(reservations: List<ConsultingReservationDateFacade>): UUID {
        var counter = 1
        println("Choose a consulting reservation")
        reservations.forEach { res ->
            println("$counter) ${res.date}")
            counter++
        }
        return reservations[readInput(counter) - 1].reservationId
    }

    fun chooseWorkoutReservation(reservations: List<WorkoutReservationDateFacade>): UUID {
        var counter = 1
        println("Choose a workout reservation")
        reservations.forEach { res ->
            println("$counter) ${res.date}")
            counter++
        }
        return reservations[readInput(counter) - 1].reservationId
    }

    fun printMsg(msg: String) {
        println(msg)
    }

    fun printConsultingReservationDetails(res: ConsultingReservationFacade) {
        println("Consulting reservation details: \n" +
                "ReservationId: ${res.id}\n" +
                "Date: ${res.date}\n" +
                "FreelancerId: ${res.freelancerId}\n" +
                "IsOpen: ${res.isOpen}")
    }

    fun printWorkoutReservationDetails(res: WorkoutReservationFacade) {
        println("Consulting reservation details: \n" +
                "ReservationId: ${res.id}\n" +
                "Date: ${res.date}\n" +
                "Aim: ${res.aim}\n" +
                "IsOpen: ${res.isOpen}")
    }
}