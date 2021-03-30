package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.application.persistence.EventStore
import it.unibo.lss.fcla.consulting.usecases.ConsultingShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.ConsultingWithGivenIdDoesNotExist
import java.time.LocalDate

class UseCasesConsultingTest : FreeSpec({

    "Cannot create two consulting with the same id" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(eventStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())

        useCasesConsulting.receiveAthleticTrainerConsulting(
            consultingId = "C001",
            memberId = "M001",
            consultingDate = LocalDate.of(2021, 1, 1),
            freelancerId = "F001",
            description = "description"
        )

        shouldThrow<ConsultingShouldHaveAUniqueId> {
            useCasesConsulting.receiveBiomechanicalConsulting(
                consultingId = "C001",
                memberId = "M001",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "F002",
                description = "description description"
            )
        }
    }

    "Cannot update a summary for a non-existent consulting" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(eventStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())

        shouldThrow<ConsultingWithGivenIdDoesNotExist> {
            useCasesConsulting.updateConsultingSummary(consultingId = "C001", description = "new description")
        }
    }

    "Retrieve profile for a given member" - {
        var eventStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(eventStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())
        val day = LocalDate.of(2021, 1, 1)

        val expectedSummaries = 2

        val firstConsulting = useCasesConsulting.receiveNutritionistConsulting(
            consultingId = "C001",
            memberId = "M001",
            consultingDate = day,
            freelancerId = "F001",
            description = "Nutritionist consulting for member M001"
        )

        aggregateRepository.save(firstConsulting)

        val secondConsulting = useCasesConsulting.receiveAthleticTrainerConsulting(
            consultingId = "C002",
            memberId = "M001",
            consultingDate = day,
            freelancerId = "F002",
            description = "AthleticTrainer consulting for member M001"
        )

        aggregateRepository.save(secondConsulting)

        val thirdConsulting = useCasesConsulting.receiveNutritionistConsulting(
            consultingId = "C003",
            memberId = "M002",
            consultingDate = day,
            freelancerId = "F001",
            description = "Nutritionist consulting for member M001"
        )

        aggregateRepository.save(thirdConsulting)

        val summaries = useCasesConsulting.retrieveProfile(memberId = "M001")

        assert(summaries.count() == expectedSummaries)
        assert(summaries.any { it.aggregateId == "C001" })
        assert(summaries.any { it.aggregateId == "C002" })
    }
})
