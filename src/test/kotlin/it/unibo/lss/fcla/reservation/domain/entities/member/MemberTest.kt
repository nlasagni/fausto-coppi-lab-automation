package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.MemberDataMustNotBeEmpty
import org.junit.jupiter.api.assertThrows

class MemberTest : FreeSpec({
    var member: Member
    "A member should" - {
        "have all data" - {
            assertThrows<MemberDataMustNotBeEmpty> {
                member = Member("", "Rossi", "0001")
                println(member)
            }
            assertThrows<MemberDataMustNotBeEmpty> {
                member = Member("Mario", "", "0001")
            }
            assertThrows<MemberDataMustNotBeEmpty> {
                member = Member("Mario", "Rossi", "")
            }
        }
        "ask for reservation list" - {
            member = Member("Mario", "Rossi", "0111")
            println("REQUESTED WORKOUT - " + member.getAllMemberRequestedWorkoutReservation())
            println("REQUESTED CONSULTING - " + member.getAllMemberRequestedConsultingReservation())
            assert(member.getAllMemberRequestedWorkoutReservation().isEmpty())
            assert(member.getAllMemberRequestedConsultingReservation().isEmpty())
        }
    }
})
