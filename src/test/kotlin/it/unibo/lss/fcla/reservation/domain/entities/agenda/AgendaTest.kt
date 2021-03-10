package it.unibo.lss.fcla.reservation.domain.entities.agenda

import io.kotest.core.spec.style.FreeSpec
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
    val freelancerId = "0111"
    val aim = "recovery"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    val consultingWorkout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())

    "A member should" - {
        "add a consulting reservation" - {
            val newAgenda = agenda.addConsultingReservation(consulting)
            assert(newAgenda.id == agenda.id)
            assert(newAgenda.retrieveConsultingReservation().contains(consulting))
        }
        "add a workout reservation" - {
            val newAgenda = agenda.addWorkoutReservation(consultingWorkout)
            assert(newAgenda.retrieveWorkoutReservation().contains(consultingWorkout))
        }
        "delete a consulting reservation" - {
            val newAgenda = agenda.addConsultingReservation(consulting)
            val deleteConsulting = newAgenda.deleteConsultingReservation(consulting)
            assert(deleteConsulting.retrieveConsultingReservation().isEmpty())
        }
        "delete a workout reservation" - {
            val newAgenda = agenda.addWorkoutReservation(consultingWorkout)
            val deleteWorkout = newAgenda.deleteWorkoutReservation(consultingWorkout)
            assert(deleteWorkout.retrieveWorkoutReservation().isEmpty())
        }
        "ask for reservation workout list" - {
            val newAgenda = agenda.addWorkoutReservation(consultingWorkout)
            assert(newAgenda.retrieveWorkoutReservation().isNotEmpty())
        }
        "ask for reservation consulting list" - {
            val newAgenda = agenda.addConsultingReservation(consulting)
            assert(newAgenda.retrieveConsultingReservation().isNotEmpty())
        }
    }
})
