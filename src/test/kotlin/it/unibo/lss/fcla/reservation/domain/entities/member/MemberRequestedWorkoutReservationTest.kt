package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.*

class MemberRequestedWorkoutReservationTest:  FreeSpec({
    var workoutReservationList = MemberRequestedWorkoutReservation()
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val aim = "Recovery"
    calendar.set(year, feb, day)
    val workout = OpenWorkoutReservation(aim, calendar.time)
    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            workoutReservationList.addWorkoutReservation(workout)
            assert(workoutReservationList.getAllMemberWorkout().isNotEmpty())
            assert(workoutReservationList.getAllMemberWorkout().contains(workout))
        }
        "be possible to delete a reservation" - {
            workoutReservationList.deleteWorkoutReservation(workout)
            assert(workoutReservationList.getAllMemberWorkout().isEmpty())
        }
    }
})