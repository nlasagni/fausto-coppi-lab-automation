/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.agenda

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Calendar
import java.util.UUID

class AgendaWorkoutReservationTest : FreeSpec({
    var consultingReservationList = AgendaWorkoutReservation()
    val calendar = Calendar.getInstance()
    val year = 2023
    val feb = 2
    val day = 25
    val aim = "recovery"
    calendar.set(year, feb, day)
    val consulting = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())

    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            val newConsultingReservationList = consultingReservationList.addWorkoutReservation(consulting)
            newConsultingReservationList.workoutReservationList.shouldNotBeEmpty()
            newConsultingReservationList.workoutReservationList.shouldContain(consulting)
        }
        "be possible to delete a reservation" - {
            val newConsultingReservationList = consultingReservationList.addWorkoutReservation(consulting)
            val deletionConsultingReservationList =
                newConsultingReservationList.deleteWorkoutReservation(consulting)
            deletionConsultingReservationList.workoutReservationList.shouldBeEmpty()
        }
    }
})
