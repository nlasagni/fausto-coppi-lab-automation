package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Calendar
import java.util.UUID

class MemberTest : FreeSpec({
    val memberId = UUID.randomUUID()
    var member = Member("Mario", "Rossi", memberId)
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val freelancerId = UUID.randomUUID()
    val aim = "recovery"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    val consultingWorkout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())

    "A member should" - {
        "have all data" - {
            shouldThrow<MemberDataMustNotBeEmpty> {
                Member("", "Rossi", memberId)
            }
            shouldThrow<MemberDataMustNotBeEmpty> {
                Member("Mario", "", memberId)
            }
        }
        "add a consulting reservation" - {
            val newMember = member.addConsultingReservation(consulting)
            newMember.firstName.shouldBe(member.firstName)
            newMember.lastName.shouldBe(member.lastName)
            newMember.id.shouldBe(member.id)
            newMember.retrieveConsultingReservation().shouldContain(consulting)
            member.toString().shouldBe(
                "Member ${newMember.firstName}, ${newMember.lastName}, ${newMember.id}"
            )
        }
        "add a workout reservation" - {
            val newMember = member.addWorkoutReservation(consultingWorkout)
            newMember.retrieveWorkoutReservation().shouldContain(consultingWorkout)
        }
        "delete a consulting reservation" - {
            val newMember = member.addConsultingReservation(consulting)
            val deleteConsulting = newMember.deleteConsultingReservation(consulting)
            deleteConsulting.retrieveConsultingReservation().shouldBeEmpty()
        }
        "delete a workout reservation" - {
            val newMember = member.addWorkoutReservation(consultingWorkout)
            val deleteWorkout = newMember.deleteWorkoutReservation(consultingWorkout)
            deleteWorkout.retrieveWorkoutReservation().shouldBeEmpty()
        }
        "ask for reservation workout list" - {
            val newMember = member.addWorkoutReservation(consultingWorkout)
            newMember.retrieveWorkoutReservation().shouldNotBeEmpty()
        }
        "ask for reservation consulting list" - {
            val newMember = member.addConsultingReservation(consulting)
            newMember.retrieveConsultingReservation().shouldNotBeEmpty()
        }
    }
})