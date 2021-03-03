package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryWithInvalidFreelancer

class ConsultingTest : FreeSpec(
    {
        "A consulting should not be created with an empty summary description" - {
            shouldThrow<ConsultingSummaryDescriptionCannotBeEmpty> {
                Consulting.createConsulting(consultingId = "123", consultingType = "Biomechanical",
                    description = "", consultingDate = Date(year = 2021, month = 1, day = 1),
                    freelancerId = "1234")
            }
        }

        "A consulting should not be created with an invalid freelancer" - {
            shouldThrow<ConsultingSummaryWithInvalidFreelancer> {
                Consulting.createConsulting(consultingId = "123", consultingType = "Biomechanical",
                    description = "Sample description", consultingDate = Date(year = 2021, month = 1, day = 1),
                    freelancerId = "")
            }
        }
    }
)
