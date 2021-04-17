/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeUUID
import io.kotest.matchers.string.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.FreelancerIdCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import java.util.Calendar
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

class OpenConsultingReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2023
    val invalidYear = 2020
    val feb = Calendar.FEBRUARY
    val day = 25
    val freelancerId = UUID.randomUUID()
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    val openConsultingReservationId = UUID.randomUUID()
    val invalidFreelancer = UUID(0, 0)

    var reservation = OpenConsultingReservation(
        validDateOfConsulting,
        freelancerId,
        openConsultingReservationId
    )

    "An Open consulting reservation should" - {
        "have a freelancer that make a consulting and a valid date" - {
            calendar.set(invalidYear, feb, day)
            val invalidDateOfConsulting = calendar.time
            shouldNotThrowAny {
                reservation = OpenConsultingReservation(
                    validDateOfConsulting,
                    freelancerId,
                    openConsultingReservationId
                )
            }

            shouldThrow<FreelancerIdCannotBeEmpty> {
                OpenConsultingReservation(
                    validDateOfConsulting,
                    invalidFreelancer,
                    openConsultingReservationId
                )
            }
            shouldThrow<OpenReservationMustNotHavePastDate> {
                OpenConsultingReservation(
                    invalidDateOfConsulting,
                    freelancerId,
                    openConsultingReservationId
                )
            }
        }
        "not to be empty" - {
            reservation.id.toString().shouldBeUUID()
            reservation.id.toString().shouldNotBeEmpty()
        }
        "have correct UUID" - {
            reservation.id.toString().shouldBeUUID()
            reservation.id.toString().shouldNotBeEmpty()
            reservation.id.shouldBe(openConsultingReservationId)
        }
        "be named as requested" - {
            assert(
                reservation.toString() ==
                    "Reservation consulting {$openConsultingReservationId} with freelancerId: " +
                    "$freelancerId in date $validDateOfConsulting"
            )
        }

        "A Member should" - {
            "be able to update correctly the date of a reservation" - {
                calendar.set(year, feb, 26)
                val newDateOfReservation = calendar.time
                val newReservation = reservation.updateDateOfConsulting(newDateOfReservation)
                println("test di correttezza valori")
                println("NewDate $newDateOfReservation")
                newReservation.freelancerId.value.shouldBe(freelancerId)
                newReservation.date.shouldBe(newDateOfReservation)
                newReservation.id.shouldBe(openConsultingReservationId)
            }
            "not to be able to update a reservation with an invalid date" - {
                calendar.set(invalidYear, feb, day)
                val invalidDateOfConsulting = calendar.time
                shouldThrow<OpenReservationMustNotHavePastDate> {
                    reservation.updateDateOfConsulting(invalidDateOfConsulting)
                }
            }
            "be able to update the freelancer of a reservation" - {
                val newFreelancerId = UUID.randomUUID()
                val updatedFreelancer = reservation.updateFreelancerOfConsulting(newFreelancerId)
                updatedFreelancer.freelancerId.value.shouldBe(newFreelancerId)
            }
            "not to be able to update a reservation with an invalid freelancer" - {
                shouldThrow<FreelancerIdCannotBeEmpty> {
                    reservation.updateFreelancerOfConsulting(invalidFreelancer)
                }
            }
        }
    }
})
