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
                memberId = "12345",
                firstName = "",
                lastName = "turing"
            )
        }
    }

    "A member should not be created without a lastname" - {
        shouldThrow<MemberLastNameCannotBeNull> {
            Member(
                memberId = "12345",
                firstName = "alan",
                lastName = ""
            )
        }
    }

    "A member cannot receive two times the same consulting" - {
        val member = Member(
            memberId = "12345",
            firstName = "alan",
            lastName = "turing"
        )

        val consultingId = "12345"
        member.receiveConsulting(consultingId, ConsultingSummary("Biomechanical",
            "description1", Date(2021, 1, 1)))

        shouldThrow<MemberConsultingAlreadyExist> {
            member.receiveConsulting(consultingId, ConsultingSummary("Biomechanical",
            "description2", Date(2021, 1, 2)))
        }
    }
})