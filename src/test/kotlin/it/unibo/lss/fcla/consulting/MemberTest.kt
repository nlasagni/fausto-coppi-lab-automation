package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingSummary
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.exceptions.MemberConsultingAlreadyExist
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

    "A member cannot receive two times the same consulting" - {
        val member = Member(
            memberId = MemberId("M-12345"),
            firstName = "alan",
            lastName = "touring"
        )
        val consultingId = ConsultingId("CS-00001")
        member.receiveConsulting(consultingId, ConsultingSummary("Biomechanics",
            "description1", Date(2021, 1, 1)))

        shouldThrow<MemberConsultingAlreadyExist> {
            member.receiveConsulting(consultingId, ConsultingSummary("Biomechanics",
            "description2", Date(2021, 1, 2)))
        }
    }
})