package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole

class FreelancerUseCases(
    private val repository: IRepository<Freelancer>
) {

    fun createAthleticTrainer(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        return createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.AthleticTrainer())
    }

    fun createPhysiotherapist(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        return createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.Physiotherapist())
    }

    fun createNutritionist(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        return createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.Nutritionist())
    }

    fun createBiomechanical(freelancerId: FreelancerId, firstName: String, lastName: String) : Freelancer {
        return createFreelancerWithRole(freelancerId, firstName, lastName, FreelancerRole.Biomechanical())
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
    fun updateFreelancerAvailabilityForDay() {

    }

    /**
     *
     */
    fun createFreelancerAvailabilityForDay() {

    }

    /**
     *
     */
    fun deleteFreelancerAvailabilityForDay() {

    }

    /**
     *
     */
    fun getFreelancerAvailabilityForDay() {

    }
}