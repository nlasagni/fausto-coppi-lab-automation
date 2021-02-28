package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
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

            Assertions.assertDoesNotThrow {
                val myReservation = OpenWorkoutReservation(
                    aim,
                    validDateOfConsulting,
                    openWorkoutReservationId
                )
                println(myReservation)
            }
            assertThrows<WorkoutReservationAimCannotBeEmpty> {
                OpenWorkoutReservation(
                    "",
                    validDateOfConsulting,
                    openWorkoutReservationId
                )
            }
            assertThrows<OpenReservationMustNotHavePastDate> {
                OpenWorkoutReservation(
                    aim,
                    invalidWorkoutDate,
                    openWorkoutReservationId
                )
            }
        }
        "not to be empty" - {
            assert(reservation.id.toString().isNotEmpty())
        }
        "have correct UUID" - {
            assert(reservation.id.toString().isNotEmpty())
            assert(reservation.id == openWorkoutReservationId)
        }
        "be named as requested" - {
            assert(
                reservation.toString() ==
                    "Reservation consulting {$openWorkoutReservationId} with aim: " +
                    "$aim in date $validDateOfConsulting"
            )
        }

        "A Member should" - {
            "be able to update correctly the date of a reservation" - {
                calendar.set(year, feb, 26)
                val newDateOfReservation = calendar.time
                val newReservation = reservation.updateWorkoutReservationDate(newDateOfReservation)
                assert(newReservation.aim == aim)
                assert(newReservation.date == newDateOfReservation)
                assert(newReservation.id == openWorkoutReservationId)
            }
            "be able to update the aim of a reservation" - {
                val newAim = "Strengthening"
                val updatedFreelancer = reservation.updateWorkoutReservationAim(newAim)
                assert(updatedFreelancer.aim == newAim)
            }
            "not to be able to update a reservation with an invalid aim" - {
                assertThrows<WorkoutReservationAimCannotBeEmpty> {
                    reservation.updateWorkoutReservationAim("")
                }
            }
        }
    }
})
