/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Calendar
import java.util.UUID

class MemberWorkoutReservationTest : FreeSpec({
    var workoutReservationList = MemberWorkoutReservation()
    val calendar = Calendar.getInstance()
    val year = 2023
    val feb = 2
    val day = 25
    val aim = "Recovery"

    calendar.set(year, feb, day)
    val workout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())
    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            val newWorkoutReservation = workoutReservationList.addWorkoutReservation(workout)
            newWorkoutReservation.workoutReservationList.shouldNotBeEmpty()
            newWorkoutReservation.workoutReservationList.shouldContain(workout)
        }
        "be possible to delete a reservation" - {
            val newWorkoutReservation = workoutReservationList.addWorkoutReservation(workout)
            val newDeletedWorkoutReservation = newWorkoutReservation.deleteWorkoutReservation(workout)
            newDeletedWorkoutReservation.workoutReservationList.shouldBeEmpty()
        }
    }
})
