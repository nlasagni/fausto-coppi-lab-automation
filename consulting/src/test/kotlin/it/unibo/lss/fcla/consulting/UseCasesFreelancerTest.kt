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
import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.exceptions.FreelancerAvailabilityAlreadyExist
import it.unibo.lss.fcla.consulting.domain.freelancer.exceptions.FreelancerAvailabilityDoesNotExist
import it.unibo.lss.fcla.consulting.usecases.FreelancerUseCases
import it.unibo.lss.fcla.consulting.usecases.freelancer.exceptions.FreelancerShouldHaveAUniqueId
import java.time.LocalDate
import java.time.LocalTime

class UseCasesFreelancerTest : FreeSpec({
    "Cannot create two freelancers with the same Id" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val useCasesFreelancer = FreelancerUseCases(aggregateRepository, MockPresenter())

        useCasesFreelancer.createAthleticTrainer(freelancerId = "F001", firstName = "Alan", lastName = "Turing")
        shouldThrow<FreelancerShouldHaveAUniqueId> {
            useCasesFreelancer.createBiomechanical(freelancerId = "F001", firstName = "Mario", lastName = "Rossi")
        }
    }

    "Test add day availabilities for freelancer" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val useCasesFreelancer = FreelancerUseCases(aggregateRepository, MockPresenter())
        val id = "F001"
        val date = LocalDate.of(2021, 1, 1)

        useCasesFreelancer.createAthleticTrainer(freelancerId = id, firstName = "Alan", lastName = "Turing")
        useCasesFreelancer.createFreelancerAvailabilityForDay(
            freelancerId = id,
            day = date,
            fromTime = LocalTime.MIN,
            toTime = LocalTime.MAX
        )

        shouldThrow<FreelancerAvailabilityAlreadyExist> {
            useCasesFreelancer.createFreelancerAvailabilityForDay(
                freelancerId = id,
                day = date,
                fromTime = LocalTime.MIN,
                toTime = LocalTime.MAX
            )
        }

        assert(
            useCasesFreelancer.getFreelancerAvailabilityForDay(freelancerId = id, day = date) ==
                AvailabilityHours(LocalTime.MIN, LocalTime.MAX)
        )
    }

    "Test update day availabilities for freelancer" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val useCasesFreelancer = FreelancerUseCases(aggregateRepository, MockPresenter())
        val id = "F001"
        val date = LocalDate.of(2021, 1, 1)

        useCasesFreelancer.createAthleticTrainer(freelancerId = id, firstName = "Alan", lastName = "Turing")
        useCasesFreelancer.createFreelancerAvailabilityForDay(
            freelancerId = id,
            day = date,
            fromTime = LocalTime.MIN,
            toTime = LocalTime.MAX
        )

        val from: LocalTime = LocalTime.MIN
        val to: LocalTime = LocalTime.MIN.plusHours(2)

        useCasesFreelancer.updateFreelancerAvailabilityForDay(
            freelancerId = id,
            day = date,
            fromTime = from,
            toTime = to
        )

        assert(
            useCasesFreelancer.getFreelancerAvailabilityForDay(freelancerId = id, day = date) ==
                AvailabilityHours(fromTime = from, toTime = to)
        )
    }

    "Test delete day availabilities for freelancer" - {
        var eventStore = EventStore()
        var aggregateRepository = FreelancerMockRepository(eventStore)
        val useCasesFreelancer = FreelancerUseCases(aggregateRepository, MockPresenter())
        val id = "F001"
        val date = LocalDate.of(2021, 1, 1)

        useCasesFreelancer.createAthleticTrainer(freelancerId = id, firstName = "Alan", lastName = "Turing")
        useCasesFreelancer.createFreelancerAvailabilityForDay(
            freelancerId = id,
            day = date,
            fromTime = LocalTime.MIN,
            toTime = LocalTime.MAX
        )

        useCasesFreelancer.deleteFreelancerAvailabilityForDay(freelancerId = id, day = date)

        shouldThrow<FreelancerAvailabilityDoesNotExist> {
            useCasesFreelancer.getFreelancerAvailabilityForDay(freelancerId = id, day = date)
        }
    }
})
