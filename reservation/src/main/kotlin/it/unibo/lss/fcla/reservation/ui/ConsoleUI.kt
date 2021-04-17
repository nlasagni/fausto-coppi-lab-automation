/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.ui

import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade
import java.util.Calendar
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Console interactive interface that simulate partial functioning of this microservice.
 *
 * The only purpose is to show functionality of this microservice
 *
 */
class ConsoleUI {

    private val freelancer1 = UUID.randomUUID()
    private val freelancer2 = UUID.randomUUID()
    private val cal = Calendar.getInstance()
    private val date7April: Date
    private val date24April: Date
    private val aim1 = "Injury recovery"
    private val aim2 = "Strengthening"

    init {
        cal.set(year, Calendar.APRIL, day7)
        date7April = cal.time
        cal.set(year, Calendar.APRIL, day24)
        date24April = cal.time
    }

    companion object {
        private const val year = 2021
        private const val day7 = 7
        private const val day24 = 24
        private const val numberOfCommands = 9

        private fun writeAllCommands() {
            println(
                "List of possible commands\n" +
                    "1) Create Consulting Reservation\n" +
                    "2) Create Workout Reservation\n" +
                    "3) Update Consulting Reservation\n" +
                    "4) Update Workout Reservation\n" +
                    "5) Delete Consulting Reservation\n" +
                    "6) Delete Workout Reservation\n" +
                    "7) Consulting Reservation Details\n" +
                    "8) Workout Reservation Details\n" +
                    "9) Close and exit"
            )
        }

        private fun readInput(max: Int): Int {
            var inputRead = false
            var validInput = 0
            do {
                val str = readLine() ?: ""
                try {
                    validInput = str.toInt()
                    if (validInput in 1..max) {
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
    }

    fun readCommand(): Int {
        writeAllCommands()
        return readInput(numberOfCommands)
    }

    fun readF(): UUID {
        println(
            "Choose a freelancer\n" +
                "1) Mario Rossi\n" +
                "2) Giovanna Bianchi\n"
        )
        return when (readInput(2)) {
            1 -> freelancer1
            else -> freelancer2
        }
    }

    fun readD(): Date {
        println(
            "Choose a date\n" +
                "1) 2021/4/7\n" +
                "2) 2021/4/24\n"
        )
        return when (readInput(2)) {
            1 -> date7April
            else -> date24April
        }
    }

    fun readA(): String {
        println(
            "Choose an aim\n" +
                "1) Injury recovery\n" +
                "2) Strengthening\n"
        )
        return when (readInput(2)) {
            1 -> aim1
            else -> aim2
        }
    }

    fun chooseConsRes(reservations: List<ConsultingReservationDateFacade>): UUID {
        var counter = 1
        println("Choose a consulting reservation")
        reservations.forEach { res ->
            println("$counter) ${res.date}")
            counter++
        }
        return reservations[readInput(reservations.size) - 1].reservationId
    }

    fun chooseWorkRes(reservations: List<WorkoutReservationDateFacade>): UUID {
        var counter = 1
        println("Choose a workout reservation")
        reservations.forEach { res ->
            println("$counter) ${res.date}")
            counter++
        }
        return reservations[readInput(reservations.size) - 1].reservationId
    }

    fun printMsg(msg: String) {
        println("\n$msg\n")
    }

    fun printConsResDetails(res: ConsultingReservationFacade) {
        println(
            "Consulting reservation details: \n" +
                "ReservationId: ${res.id}\n" +
                "Date: ${res.date}\n" +
                "FreelancerId: ${res.freelancerId}\n" +
                "IsOpen: ${res.isOpen}"
        )
    }

    fun printWorkResDetails(res: WorkoutReservationFacade) {
        println(
            "Consulting reservation details: \n" +
                "ReservationId: ${res.id}\n" +
                "Date: ${res.date}\n" +
                "Aim: ${res.aim}\n" +
                "IsOpen: ${res.isOpen}"
        )
    }
}
