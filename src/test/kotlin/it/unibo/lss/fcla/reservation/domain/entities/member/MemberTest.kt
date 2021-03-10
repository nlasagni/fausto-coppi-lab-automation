package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import org.junit.jupiter.api.assertThrows
import java.util.Calendar
import java.util.UUID

class MemberTest : FreeSpec({
    val memberId = UUID.randomUUID()
    var member = Member("Mario", "Rossi", memberId)
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val freelancerId = "0111"
    val aim = "recovery"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    val consultingWorkout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())

    "A member should" - {
        "have all data" - {
            assertThrows<MemberDataMustNotBeEmpty> {
                member = Member("", "Rossi", memberId)
                println(member)
            }
            assertThrows<MemberDataMustNotBeEmpty> {
                member = Member("Mario", "", memberId)
            }
        }
        "add a consulting reservation" - {
            val newMember = member.addConsultingReservation(consulting)
            assert(newMember.firstName == member.firstName)
            assert(newMember.lastName == member.lastName)
            assert(newMember.id == member.id)
            assert(newMember.retrieveConsultingReservation().contains(consulting))
            assert(member.toString() == "Member ${newMember.firstName}, ${newMember.lastName}, ${newMember.id}")
        }
        "add a workout reservation" - {
            val newMember = member.addWorkoutReservation(consultingWorkout)
            assert(newMember.retrieveWorkoutReservation().contains(consultingWorkout))
        }
        "delete a consulting reservation" - {
            val newMember = member.addConsultingReservation(consulting)
            val deleteConsulting = newMember.deleteConsultingReservation(consulting)
            assert(deleteConsulting.retrieveConsultingReservation().isEmpty())
        }
        "delete a workout reservation" - {
            val newMember = member.addWorkoutReservation(consultingWorkout)
            val deleteWorkout = newMember.deleteWorkoutReservation(consultingWorkout)
            assert(deleteWorkout.retrieveWorkoutReservation().isEmpty())
        }
        "ask for reservation workout list" - {
            val newMember = member.addWorkoutReservation(consultingWorkout)
            assert(newMember.retrieveWorkoutReservation().isNotEmpty())
        }
        "ask for reservation consulting list" - {
            val newMember = member.addConsultingReservation(consulting)
            assert(newMember.retrieveConsultingReservation().isNotEmpty())
        }
    }
})
