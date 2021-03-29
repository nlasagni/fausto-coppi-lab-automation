package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.createAthleticTrainer
import it.unibo.lss.fcla.consulting.domain.freelancer.createBiomechanical
import it.unibo.lss.fcla.consulting.domain.freelancer.createNutritionist
import it.unibo.lss.fcla.consulting.domain.freelancer.createPhysiotherapist
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerAvailabilityFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerErrorFacade
import it.unibo.lss.fcla.consulting.usecases.facades.FreelancerFacade
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
     * FLAC-14 Create new [Freelancer]
     */
    fun createAthleticTrainer(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer? {

        return try {
            if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
            val freelancer = Freelancer.createAthleticTrainer(freelancerId, firstName, lastName)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))

            freelancer
        }catch (e: FreelancerShouldHaveAUniqueId) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
    }

    /**
     * FLAC-14 Create new [Freelancer]
     */
    fun createPhysiotherapist(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer? {

        return try {
            if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
            val freelancer = Freelancer.createPhysiotherapist(freelancerId, firstName, lastName)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))
            freelancer
        }catch (e: FreelancerShouldHaveAUniqueId) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
    }

    /**
     * FLAC-14 Create new [Freelancer]
     */
    fun createNutritionist(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer? {

        return try {
            if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
            val freelancer = Freelancer.createNutritionist(freelancerId, firstName, lastName)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))

            freelancer
        }catch (e: FreelancerShouldHaveAUniqueId) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
    }

    /**
     * FLAC-14 Create new [Freelancer]
     */
    fun createBiomechanical(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer? {
        return try {
            if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
            val freelancer = Freelancer.createBiomechanical(freelancerId, firstName, lastName)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))

            freelancer
        }catch (e: FreelancerShouldHaveAUniqueId) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
    }

    /**
     * FLAC-15 Manage freelancer availabilities
     */
    fun updateFreelancerAvailabilityForDay(
        freelancerId: FreelancerId,
        day: LocalDate,
        fromTime: LocalTime,
        toTime: LocalTime
    ): Freelancer? {

        return try {
            if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
            val freelancer = rehydrateFreelancer(freelancerId)
            freelancer.updateAvailability(availabilityDate = day, fromTime = fromTime, toTime = toTime)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))

            freelancer
        }catch (e: FreelancerWithGivenIdDoesNotExist) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }

    }

    /**
     * FLAC-15 Manage freelancer availabilities
     */
    fun createFreelancerAvailabilityForDay(
        freelancerId: FreelancerId,
        day: LocalDate,
        fromTime: LocalTime,
        toTime: LocalTime
    ): Freelancer? {

        return try {
            if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
            val freelancer = rehydrateFreelancer(freelancerId)
            freelancer.addAvailability(newAvailabilityDate = day, fromTime = fromTime, toTime = toTime)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))

            freelancer
        }catch (e: FreelancerWithGivenIdDoesNotExist) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
    }

    /**
     * FLAC-15 Manage freelancer availabilities
     */
    fun deleteFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: LocalDate): Freelancer? {

        return try {
            if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
            val freelancer = rehydrateFreelancer(freelancerId)
            freelancer.deleteAvailability(availabilityDate = day)
            repository.save(freelancer)

            presenter.onResult(FreelancerFacade.create(freelancer))

            freelancer
        }catch (e: FreelancerWithGivenIdDoesNotExist) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
    }

    /**
     * FLAC-16 Check freelancer availabilities
     */
    fun getFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: LocalDate): AvailabilityHours? {

        return try {
            if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
            val freelancer = rehydrateFreelancer(freelancerId)

            presenter.onResult(FreelancerAvailabilityFacade.create(day, freelancer.getAvailabilityForDay(day)))

            freelancer.getAvailabilityForDay(day)
        }catch (e: FreelancerWithGivenIdDoesNotExist) {
            presenter.onResult(FreelancerErrorFacade.create(e.message ?: ""))
            null
        }
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
