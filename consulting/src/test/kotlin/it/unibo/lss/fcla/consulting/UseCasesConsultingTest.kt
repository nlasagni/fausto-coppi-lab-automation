/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.application.adapters.EventStore
import it.unibo.lss.fcla.consulting.usecases.ConsultingUseCases
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases
import it.unibo.lss.fcla.consulting.usecases.exceptions.ConsultingShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.exceptions.ConsultingWithGivenIdDoesNotExist
import it.unibo.lss.fcla.consulting.usecases.exceptions.FreelancerWithGivenIdDoesNotExist
import it.unibo.lss.fcla.consulting.usecases.exceptions.IncompatibleFreelancerRoleForConsulting
import java.time.LocalDate

class UseCasesConsultingTest : FreeSpec({

    "Cannot create two consulting with the same id" - {
        var eventStore = EventStore()
        var freelancerStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(freelancerStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())
        val useCasesFreelancer = FreelancerUseCases(freelancerRepository, MockPresenter())

        useCasesFreelancer.createAthleticTrainer(
            freelancerId = "F1",
            firstName = "Mario",
            lastName = "Rossi"
        )

        useCasesConsulting.receiveAthleticTrainerConsulting(
            consultingId = "C001",
            memberId = "M001",
            consultingDate = LocalDate.of(2021, 1, 1),
            freelancerId = "F1",
            description = "description"
        )

        shouldThrow<ConsultingShouldHaveAUniqueId> {
            useCasesConsulting.receiveAthleticTrainerConsulting(
                consultingId = "C001",
                memberId = "M001",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "F1",
                description = "description description"
            )
        }
    }

    "Cannot update a summary for a non-existent consulting" - {
        var eventStore = EventStore()
        var freelancerStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(freelancerStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())

        shouldThrow<ConsultingWithGivenIdDoesNotExist> {
            useCasesConsulting.updateConsultingSummary(consultingId = "C001", description = "new description")
        }
    }

    "Retrieve profile for a given member" - {
        var eventStore = EventStore()
        var freelancerStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(freelancerStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())
        val day = LocalDate.of(2021, 1, 1)
        val useCasesFreelancer = FreelancerUseCases(freelancerRepository, MockPresenter())

        useCasesFreelancer.createAthleticTrainer(
            freelancerId = "F1",
            firstName = "Mario",
            lastName = "Rossi"
        )

        useCasesFreelancer.createNutritionist(
            freelancerId = "F2",
            firstName = "Mario",
            lastName = "Bianchi"
        )

        val expectedSummaries = 2

        useCasesConsulting.receiveNutritionistConsulting(
            consultingId = "C001",
            memberId = "M001",
            consultingDate = day,
            freelancerId = "F2",
            description = "Nutritionist consulting for member M001"
        )

        useCasesConsulting.receiveAthleticTrainerConsulting(
            consultingId = "C002",
            memberId = "M001",
            consultingDate = day,
            freelancerId = "F1",
            description = "AthleticTrainer consulting for member M001"
        )

        useCasesConsulting.receiveNutritionistConsulting(
            consultingId = "C003",
            memberId = "M002",
            consultingDate = day,
            freelancerId = "F2",
            description = "Nutritionist consulting for member M001"
        )

        val summaries = useCasesConsulting.retrieveProfile(memberId = "M001")

        assert(summaries.count() == expectedSummaries)
        assert(summaries.any { it.aggregateId == "C001" })
        assert(summaries.any { it.aggregateId == "C002" })
    }

    "A consulting should be created with an existing freelancer" - {
        var eventStore = EventStore()
        var freelancerStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(freelancerStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())
        val useCasesFreelancer = FreelancerUseCases(freelancerRepository, MockPresenter())

        useCasesFreelancer.createAthleticTrainer(
            freelancerId = "F1",
            firstName = "Mario",
            lastName = "Rossi"
        )

        useCasesConsulting.receiveAthleticTrainerConsulting(
            consultingId = "C001",
            memberId = "M1",
            consultingDate = LocalDate.of(2021, 1, 1),
            freelancerId = "F1",
            description = "Sample data"
        )

        shouldThrow<FreelancerWithGivenIdDoesNotExist> {
            useCasesConsulting.receiveAthleticTrainerConsulting(
                consultingId = "C002",
                memberId = "M1",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "F2",
                description = "Sample data"
            )
        }
    }

    "A consulting should be executed by the compatible freelancer role" - {
        var eventStore = EventStore()
        var freelancerStore = EventStore()
        var aggregateRepository = ConsultingMockRepository(eventStore)
        var freelancerRepository = FreelancerMockRepository(freelancerStore)
        val useCasesConsulting = ConsultingUseCases(aggregateRepository, freelancerRepository, MockPresenter())
        val useCasesFreelancer = FreelancerUseCases(freelancerRepository, MockPresenter())

        useCasesFreelancer.createAthleticTrainer(
            freelancerId = "F1",
            firstName = "Mario",
            lastName = "Rossi"
        )

        shouldThrow<IncompatibleFreelancerRoleForConsulting> {
            useCasesConsulting.receivePhysiotherapyConsulting(
                consultingId = "C001",
                memberId = "M1",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "F1",
                description = "Sample data"
            )
        }
    }
})
