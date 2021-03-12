package it.unibo.lss.fcla.reservation.domain.entities.agenda

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Calendar
import java.util.UUID

class AgendaTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    var agenda = Agenda(agendaId)
    val calendar = Calendar.getInstance()
    val year = 2022
    val feb = 2
    val day = 25
    val freelancerId = UUID.randomUUID()
    val aim = "recovery"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    val consultingWorkout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())

    "A member should" - {
        "add a consulting reservation" - {
            val newAgenda = agenda.addConsultingReservation(consulting)
            newAgenda.id.shouldBeEqualComparingTo(agenda.id)
            newAgenda.retrieveConsultingReservation().shouldContain(consulting)
        }
        "add a workout reservation" - {
            val newAgenda = agenda.addWorkoutReservation(consultingWorkout)
            newAgenda.retrieveWorkoutReservation().shouldContain(consultingWorkout)
        }
        "delete a consulting reservation" - {
            val newAgenda = agenda.addConsultingReservation(consulting)
            val deleteConsulting = newAgenda.deleteConsultingReservation(consulting)
            deleteConsulting.retrieveConsultingReservation().shouldBeEmpty()
        }
        "delete a workout reservation" - {
            val newAgenda = agenda.addWorkoutReservation(consultingWorkout)
            val deleteWorkout = newAgenda.deleteWorkoutReservation(consultingWorkout)
            deleteWorkout.retrieveWorkoutReservation().shouldBeEmpty()
        }
        "ask for reservation workout list" - {
            val newAgenda = agenda.addWorkoutReservation(consultingWorkout)
            newAgenda.retrieveWorkoutReservation().shouldNotBeEmpty()
        }
        "ask for reservation consulting list" - {
            val newAgenda = agenda.addConsultingReservation(consulting)
            newAgenda.retrieveConsultingReservation().shouldNotBeEmpty()
        }
    }
})
