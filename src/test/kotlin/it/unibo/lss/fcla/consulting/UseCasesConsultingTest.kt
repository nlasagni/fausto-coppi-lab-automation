package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.usecases.ConsultingShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.ConsultingWithGivenIdDoesNotExist

class UseCasesConsultingTest : FreeSpec({

    "Cannot create two consulting with the same id" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository)

        useCasesConsulting.receiveAthleticTrainerConsulting(
            consultingId = "C001",
            memberId = "M001",
            consultingDate = Date(2021, 1, 1),
            freelancerId = "F001",
            description = "description"
        )

        shouldThrow<ConsultingShouldHaveAUniqueId> {
            useCasesConsulting.receiveBiomechanicalConsulting(
                consultingId = "C001",
                memberId = "M001",
                consultingDate = Date(2021, 1, 1),
                freelancerId = "F002",
                description = "description description"
            )
        }
    }

    "Cannot update a summary for a non-existent consulting" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository)

        shouldThrow<ConsultingWithGivenIdDoesNotExist> {
            useCasesConsulting.updateConsultingSummary(consultingId = "C001", memberId = "M001", "new description")
        }
    }
})
