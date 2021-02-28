package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.WorkoutReservationAimCannotBeEmpty
import org.junit.jupiter.api.assertThrows
import java.util.Calendar
import java.util.UUID

class CloseWorkoutReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    calendar.set(year, feb, day)
    val dateOfConsulting = calendar.time
    val aim = "recovery"
    val closeWorkoutId = UUID.randomUUID()

    "A CloseWorkoutReservation should" - {
        "not to be empty" - {
            val reservation = CloseWorkoutReservation(aim, dateOfConsulting, closeWorkoutId)
            assert(reservation.id.toString().isNotEmpty())
            assert(reservation.id == closeWorkoutId)
        }
        "be named as requested" - {
            val reservation = CloseWorkoutReservation(aim, dateOfConsulting, closeWorkoutId)
            assert(
                reservation.toString() ==
                    "Reservation workout {$closeWorkoutId} with aim: " +
                    "$aim in date $dateOfConsulting"
            )
        }
        "have a aim of workout" - {
            assertThrows<WorkoutReservationAimCannotBeEmpty> {
                CloseWorkoutReservation("", dateOfConsulting, closeWorkoutId)
            }
        }
    }
})
