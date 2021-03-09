package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.createAthleticTrainer
import it.unibo.lss.fcla.consulting.domain.freelancer.createBiomechanical
import it.unibo.lss.fcla.consulting.domain.freelancer.createNutritionist
import it.unibo.lss.fcla.consulting.domain.freelancer.createPhysiotherapist
import java.time.LocalTime

class FreelancerUseCases(
    private val repository: IRepository<Freelancer>
) {

    fun createAthleticTrainer(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {
        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createAthleticTrainer(freelancerId, firstName, lastName)
        repository.save(freelancer)

        return freelancer
    }

    fun createPhysiotherapist(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {
        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createPhysiotherapist(freelancerId, firstName, lastName)
        repository.save(freelancer)

        return freelancer
    }

    fun createNutritionist(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {
        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createNutritionist(freelancerId, firstName, lastName)
        repository.save(freelancer)

        return freelancer
    }

    fun createBiomechanical(freelancerId: FreelancerId, firstName: String, lastName: String): Freelancer {
        if (freelancerExist(freelancerId)) throw FreelancerShouldHaveAUniqueId()
        val freelancer = Freelancer.createBiomechanical(freelancerId, firstName, lastName)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun updateFreelancerAvailabilityForDay(
        freelancerId: FreelancerId,
        day: Date,
        fromTime: LocalTime,
        toTime: LocalTime
    ): Freelancer {
        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        freelancer.updateAvailability(availabilityDate = day, fromTime = fromTime, toTime = toTime)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun createFreelancerAvailabilityForDay(
        freelancerId: FreelancerId,
        day: Date,
        fromTime: LocalTime,
        toTime: LocalTime
    ): Freelancer {
        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        freelancer.addAvailability(newAvailabilityDate = day, fromTime = fromTime, toTime = toTime)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun deleteFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: Date): Freelancer {

        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        freelancer.deleteAvailability(availabilityDate = day)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun getFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: Date): AvailabilityHours {
        if (!freelancerExist(freelancerId)) throw FreelancerWithGivenIdDoesNotExist()
        val freelancer = rehydrateFreelancer(freelancerId)
        return freelancer.getAvailabilityForDay(day)
    }

    private fun rehydrateFreelancer(freelancerId: FreelancerId) = Freelancer.rehydrateFreelancer(
        freelancerId,
        repository.getById(freelancerId)
    )

    private fun freelancerExist(freelancerId: FreelancerId) = repository.getById(freelancerId).count() > 0
}
