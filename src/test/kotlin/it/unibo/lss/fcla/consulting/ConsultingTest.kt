package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.domain.consulting.*
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidId
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingMustHaveAValidMember
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryMustHaveAValidFreelancer

class ConsultingTest : FreeSpec({
    "A consulting should be created with a valid Id" - {
        shouldThrow<ConsultingMustHaveAValidId> {
            Consulting.createNutritionistConsulting(consultingId = "", memberId = "123",
                consultingDate = Date(2021, 1, 1), freelancerId = "1234",
                description = "description")
        }
    }

    "A consulting should be created with a valid member" - {
        shouldThrow<ConsultingMustHaveAValidMember> {
            Consulting.createPhysiotherapyConsulting(consultingId = "1234", memberId = "",
                consultingDate = Date(2021, 1, 1), freelancerId = "1234",
                description = "description")
        }
    }

    "A consulting summary should be created with a valid freelancer" - {
        shouldThrow<ConsultingSummaryMustHaveAValidFreelancer> {
            Consulting.createPhysiotherapyConsulting(consultingId = "1234", memberId = "123",
                consultingDate = Date(2021, 1, 1), freelancerId = "",
                description = "description")
        }
    }

    "A consulting summary should be created with no empty description" - {
        shouldThrow<ConsultingSummaryDescriptionCannotBeEmpty> {
            Consulting.createPhysiotherapyConsulting(consultingId = "1234", memberId = "123",
                consultingDate = Date(2021, 1, 1), freelancerId = "F1234",
                description = "")
        }
    }
})
