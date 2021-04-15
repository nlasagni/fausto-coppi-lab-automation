/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeUUID
import io.kotest.matchers.string.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.AimCannotBeEmpty
import java.util.Calendar
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

class CloseWorkoutReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2023
    val feb = 2
    val day = 25
    calendar.set(year, feb, day)
    val dateOfConsulting = calendar.time
    val aim = "recovery"
    val closeWorkoutId = UUID.randomUUID()

    "A CloseWorkoutReservation should" - {
        "not to be empty" - {
            val reservation = CloseWorkoutReservation(aim, dateOfConsulting, closeWorkoutId)
            reservation.id.toString().shouldBeUUID()
            reservation.id.toString().shouldNotBeEmpty()
            reservation.id.shouldBe(closeWorkoutId)
            reservation.id.hashCode().shouldBe(closeWorkoutId.hashCode())
        }
        "be named as requested" - {
            val reservation = CloseWorkoutReservation(aim, dateOfConsulting, closeWorkoutId)
            reservation.toString().shouldBe(
                "Reservation workout {$closeWorkoutId} with aim: " +
                    "$aim in date $dateOfConsulting"
            )
        }
        "have a aim of workout" - {
            shouldThrow<AimCannotBeEmpty> {
                CloseWorkoutReservation("", dateOfConsulting, closeWorkoutId)
            }
        }
    }
})
