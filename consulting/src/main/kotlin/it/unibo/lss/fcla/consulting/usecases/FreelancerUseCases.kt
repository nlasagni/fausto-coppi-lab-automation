/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.createAthleticTrainer
import it.unibo.lss.fcla.consulting.domain.freelancer.createBiomechanical
import it.unibo.lss.fcla.consulting.domain.freelancer.createNutritionist
import it.unibo.lss.fcla.consulting.domain.freelancer.createPhysiotherapist
import it.unibo.lss.fcla.consulting.usecases.freelancer.exceptions.FreelancerShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.freelancer.exceptions.FreelancerWithGivenIdDoesNotExist
import it.unibo.lss.fcla.consulting.usecases.freelancer.facades.FreelancerAvailabilityFacade
import it.unibo.lss.fcla.consulting.usecases.freelancer.facades.FreelancerFacade
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * This class contains all the use cases identified in the analysis phase, which allow to interact with
 * behaviour and data of the [Freelancer] aggregate.
 */
class FreelancerUseCases(
    private val repository: IRepository<Freelancer>,
    private val presenter: IPresenter
) {

    /**
     * FLAC-9 Create new [Freelancer]
     */
    fun createAthleticTrainer(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {

        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createAthleticTrainer(freelancerId, firstName, lastName)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))

        return freelancer
    }

    /**
     * FLAC-9 Create new [Freelancer]
     */
    fun createPhysiotherapist(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {

        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createPhysiotherapist(freelancerId, firstName, lastName)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))
        return freelancer
    }

    /**
     * FLAC-9 Create new [Freelancer]
     */
    fun createNutritionist(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {

        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createNutritionist(freelancerId, firstName, lastName)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))

        return freelancer
    }

    /**
     * FLAC-9 Create new [Freelancer]
     */
    fun createBiomechanical(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {

        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createBiomechanical(freelancerId, firstName, lastName)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))

        return freelancer
    }

    /**
     * FLAC-10 Manage freelancer availabilities
     */
    fun updateFreelancerAvailabilityForDay(
        freelancerId: FreelancerId,
        day: LocalDate,
        fromTime: LocalTime,
        toTime: LocalTime
    ): Freelancer {

        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        freelancer.updateAvailability(availabilityDate = day, fromTime = fromTime, toTime = toTime)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))

        return freelancer
    }

    /**
     * FLAC-10 Manage freelancer availabilities
     */
    fun createFreelancerAvailabilityForDay(
        freelancerId: FreelancerId,
        day: LocalDate,
        fromTime: LocalTime,
        toTime: LocalTime
    ): Freelancer {

        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        freelancer.addAvailability(newAvailabilityDate = day, fromTime = fromTime, toTime = toTime)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))

        return freelancer
    }

    /**
     * FLAC-10 Manage freelancer availabilities
     */
    fun deleteFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: LocalDate): Freelancer? {

        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        freelancer.deleteAvailability(availabilityDate = day)
        repository.save(freelancer)

        presenter.onResult(FreelancerFacade.create(freelancer))

        return freelancer
    }

    /**
     * FLAC-11 Check freelancer availabilities
     */
    fun getFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: LocalDate): AvailabilityHours? {

        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)

        presenter.onResult(FreelancerAvailabilityFacade.create(day, freelancer.getAvailabilityForDay(day)))

        return freelancer.getAvailabilityForDay(day)
    }

    /**
     * Utility method to rehydrate a [Freelancer] aggregate
     */
    private fun rehydrateFreelancer(freelancerId: FreelancerId) = Freelancer.rehydrateFreelancer(
        freelancerId,
        repository.getById(freelancerId)
    )

    /**
     * Utility method that check if the given [freelancerId] exist
     */
    private fun freelancerExist(freelancerId: FreelancerId) = repository.getById(freelancerId).count() > 0
}
