package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerCreatedEvent

/**
 *
 */
fun Freelancer.Companion.createPhysiotherapist(freelancerId: FreelancerId, firstName: String,
                                               lastName: String) : Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(FreelancerCreatedEvent(freelancerId, firstName, lastName,
        FreelancerRole.Physiotherapist()))

    return freelancer
}

/**
 *
 */
fun Freelancer.Companion.createNutritionist(freelancerId: FreelancerId, firstName: String,
                                               lastName: String) : Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(FreelancerCreatedEvent(freelancerId, firstName, lastName,
        FreelancerRole.Nutritionist()))

    return freelancer
}

/**
 *
 */
fun Freelancer.Companion.createAthleticTrainer(freelancerId: FreelancerId, firstName: String,
                                            lastName: String) : Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(FreelancerCreatedEvent(freelancerId, firstName, lastName,
        FreelancerRole.AthleticTrainer()))

    return freelancer
}

/**
 *
 */
fun Freelancer.Companion.createBiomechanical(freelancerId: FreelancerId, firstName: String,
                                            lastName: String) : Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(FreelancerCreatedEvent(freelancerId, firstName, lastName,
        FreelancerRole.Biomechanical()))

    return freelancer
}