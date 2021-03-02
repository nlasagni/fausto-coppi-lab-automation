package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberLastNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.member.Member
import it.unibo.lss.fcla.consulting.domain.member.MemberId

class MemberTest : FreeSpec({

    "A member should not be created without a firstname" - {
        shouldThrow<MemberFirstNameCannotBeNull> {
            Member(
                memberId = MemberId("M-12345"),
                firstName = "",
                lastName = "touring"
            )
        }
    }

    "A member should not be created without a lastname" - {
        shouldThrow<MemberLastNameCannotBeNull> {
            Member(
                memberId = MemberId("M-12345"),
                firstName = "alan",
                lastName = ""
            )
        }
    }

    "A member should have a valid id" - {
        shouldThrow<IllegalArgumentException> {
            Member(
                memberId = MemberId("123"),
                firstName = "",
                lastName = "touring"
            )
        }
    }
})