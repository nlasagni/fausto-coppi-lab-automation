package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import java.util.UUID

class MemberLedgerTest : FreeSpec({
    val ledgerId = UUID.randomUUID()
    val memberList = MemberLedger(ledgerId)
    val member = Member("Mario", "Rossi", UUID.randomUUID())

    "A member should" - {
        "be added to the ledger" - {
            val newMemberList = memberList.addMemberToLedger(member)
            assert(memberList.id == newMemberList.id)
            assert(newMemberList.retrieveAllMembers().contains(member))
        }
    }
})
