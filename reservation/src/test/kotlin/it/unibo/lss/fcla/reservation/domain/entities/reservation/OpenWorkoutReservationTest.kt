package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeUUID
import io.kotest.matchers.string.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.AimCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import java.util.Calendar
import java.util.UUID

class OpenWorkoutReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2022
    val invalidYear = 2021
    val feb = Calendar.FEBRUARY
    val day = 25
    val aim = "recovery"
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    val openWorkoutReservationId = UUID.randomUUID()

    var reservation = OpenWorkoutReservation(
        aim,
        validDateOfConsulting,
        openWorkoutReservationId
    )

    "An Open consulting reservation should" - {
        "have an aim and a valid date" - {
            calendar.set(invalidYear, feb, day)
            val invalidWorkoutDate = calendar.time

            shouldNotThrowAny {
                val myReservation = OpenWorkoutReservation(
                    aim,
                    validDateOfConsulting,
                    openWorkoutReservationId
                )
                println(myReservation)
            }
            shouldThrow<AimCannotBeEmpty> {
                OpenWorkoutReservation(
                    "",
                    validDateOfConsulting,
                    openWorkoutReservationId
                )
            }
            shouldThrow<OpenReservationMustNotHavePastDate> {
                OpenWorkoutReservation(
                    aim,
                    invalidWorkoutDate,
                    openWorkoutReservationId
                )
            }
        }
        "not to be empty" - {
            reservation.id.toString().shouldBeUUID()
            reservation.id.toString().shouldNotBeEmpty()
        }
        "have correct UUID" - {
            reservation.id.toString().shouldNotBeEmpty()
            reservation.id.shouldBe(openWorkoutReservationId)
        }
        "be named as requested" - {
            reservation.toString().shouldBe(
                "Reservation consulting {$openWorkoutReservationId} with aim: " +
                    "$aim in date $validDateOfConsulting"
            )
        }

        "A Member should" - {
            "be able to update correctly the date of a reservation" - {
                calendar.set(year, feb, 26)
                val newDateOfReservation = calendar.time
                val newReservation = reservation.updateWorkoutReservationDate(newDateOfReservation)
                newReservation.aim.value.shouldBe(aim)
                newReservation.date.shouldBe(newDateOfReservation)
                newReservation.id.shouldBe(openWorkoutReservationId)
            }
            "be able to update the aim of a reservation" - {
                val newAim = "Strengthening"
                val updatedFreelancer = reservation.updateWorkoutReservationAim(newAim)
                updatedFreelancer.aim.value.shouldBe(newAim)
            }
            "not to be able to update a reservation with an invalid aim" - {
                shouldThrow<AimCannotBeEmpty> {
                    reservation.updateWorkoutReservationAim("")
                }
            }
        }
    }
})
