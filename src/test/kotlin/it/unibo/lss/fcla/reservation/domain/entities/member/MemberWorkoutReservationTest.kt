package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Calendar
import java.util.UUID

class MemberWorkoutReservationTest : FreeSpec({
    var workoutReservationList = MemberWorkoutReservation()
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val aim = "Recovery"

    calendar.set(year, feb, day)
    val workout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())
    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            val newWorkoutReservation = workoutReservationList.addWorkoutReservation(workout)
            assert(newWorkoutReservation.workoutReservationList.isNotEmpty())
            assert(newWorkoutReservation.workoutReservationList.contains(workout))
        }
        "be possible to delete a reservation" - {
            val newWorkoutReservation = workoutReservationList.addWorkoutReservation(workout)
            val newDeletedWorkoutReservation = newWorkoutReservation.deleteWorkoutReservation(workout)
            assert(newDeletedWorkoutReservation.workoutReservationList.isEmpty())
        }
    }
})
