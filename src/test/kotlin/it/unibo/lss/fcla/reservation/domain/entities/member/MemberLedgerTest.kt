package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import org.junit.jupiter.api.assertThrows
import java.util.Calendar
import java.util.UUID

class MemberLedgerTest : FreeSpec({
    val ledgerId = UUID.randomUUID()
    val memberLedger = MemberLedger(ledgerId)
    val member = Member("Mario", "Rossi", UUID.randomUUID())

    val calendar = Calendar.getInstance()
    val year = 2022
    val feb = Calendar.FEBRUARY
    val day = 25
    val freelancerId = UUID.randomUUID()
    val freelancer = UUID.randomUUID()
    val aim = "recovery"
    val aimInjury = "injury"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    val consultingNotInMember = OpenConsultingReservation(calendar.time, freelancer, UUID.randomUUID())
    val workout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())
    val workoutNotInMember = OpenWorkoutReservation(aimInjury, calendar.time, UUID.randomUUID())

    "A member should" - {
        "be added to the ledger" - {
            val newMemberList = memberLedger.addMemberToLedger(member)
            assert(memberLedger.id == newMemberList.id)
            assert(newMemberList.retrieveAllMembers().contains(member))
        }
    }
    "It should" - {
        "be possible to retrieve a member given a reservation" - {
            val memberWithConsulting = member.addConsultingReservation(consulting)
            val memberWithWorkout = memberWithConsulting.addWorkoutReservation(workout)
            val myLedgerWithConsulting = memberLedger.addMemberToLedger(memberWithConsulting)
            val myLedgerWithConsultingAndWorkout = memberLedger.addMemberToLedger(memberWithWorkout)
            assert(myLedgerWithConsulting.retrieveMemberWithConsultingReservation(consulting) == memberWithConsulting)
            assert(
                myLedgerWithConsultingAndWorkout.retrieveMemberWithWorkoutReservation(workout)
                    == memberWithWorkout
            )
            assertThrows<NoSuchElementException> {
                myLedgerWithConsulting.retrieveMemberWithConsultingReservation(consultingNotInMember)
            }
            assertThrows<NoSuchElementException> {
                myLedgerWithConsultingAndWorkout.retrieveMemberWithWorkoutReservation(workoutNotInMember)
            }
        }
    }
})
