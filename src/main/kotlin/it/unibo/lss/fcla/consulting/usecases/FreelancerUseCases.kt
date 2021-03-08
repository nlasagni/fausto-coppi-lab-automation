package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import java.time.LocalTime

class FreelancerUseCases(
    private val repository: IRepository<Freelancer>
) {

    fun createAthleticTrainer(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        if (repository.getById(freelancerId).count() > 0) {
            throw FreelancerShouldHaveAUniqueId()
        }
        val freelancer = createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.AthleticTrainer())
        repository.save(freelancer)

        return freelancer
    }

    fun createPhysiotherapist(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        if (repository.getById(freelancerId).count() > 0) {
            throw FreelancerShouldHaveAUniqueId()
        }
        val freelancer = createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.Physiotherapist())
        repository.save(freelancer)

        return freelancer
    }

    fun createNutritionist(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        if (repository.getById(freelancerId).count() > 0) {
            throw FreelancerShouldHaveAUniqueId()
        }
        val freelancer = createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.Nutritionist())
        repository.save(freelancer)

        return freelancer
    }

    fun createBiomechanical(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        if (repository.getById(freelancerId).count() > 0) {
            throw FreelancerShouldHaveAUniqueId()
        }
        val freelancer = createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.Biomechanical())
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    private fun createFreelancerWithRole(freelancerId: FreelancerId, firstName: String,
                                         lastName: String, role: FreelancerRole) : Freelancer {
        return Freelancer.createFreelancer(freelancerId, firstName, lastName, role)
    }

    /**
     *
     */
    fun updateFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: Date, fromTime: LocalTime,
                                           toTime: LocalTime) : Freelancer {
        if (repository.getById(freelancerId).count() == 0) {
            throw FreelancerWithGivenIdDoesNotExist()
        }
        val freelancer = Freelancer.rehydrateFreelancer(freelancerId, repository.getById(freelancerId))
        freelancer.updateAvailability(availabilityDate = day, fromTime = fromTime, toTime = toTime)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun createFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: Date, fromTime: LocalTime,
                                           toTime: LocalTime) : Freelancer {
        if (repository.getById(freelancerId).count() == 0) {
            throw FreelancerWithGivenIdDoesNotExist()
        }
        val freelancer = Freelancer.rehydrateFreelancer(freelancerId, repository.getById(freelancerId))
        freelancer.addAvailability(newAvailabilityDate = day, fromTime = fromTime, toTime = toTime)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun deleteFreelancerAvailabilityForDay(freelancerId: FreelancerId, day: Date, fromTime: LocalTime,
                                           toTime: LocalTime) : Freelancer {

        if (repository.getById(freelancerId).count() == 0) {
            throw FreelancerWithGivenIdDoesNotExist()
        }
        val freelancer = Freelancer.rehydrateFreelancer(freelancerId, repository.getById(freelancerId))
        freelancer.deleteAvailability(availabilityDate = day)
        repository.save(freelancer)

        return freelancer
    }

    /**
     *
     */
    fun getFreelancerAvailabilityForDay() {

    }

    private fun rehydrateFreelancer(freelancerId: FreelancerId) = Freelancer.rehydrateFreelancer(
        freelancerId, repository.getById(freelancerId)
    )

    private fun freelancerExist(freelancerId: FreelancerId) = repository.getById(freelancerId).count() > 0
}