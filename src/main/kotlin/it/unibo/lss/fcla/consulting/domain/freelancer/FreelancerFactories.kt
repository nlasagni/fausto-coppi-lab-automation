package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.domain.freelancer.events.FreelancerCreatedEvent

/**
 * Extension method factory for Freelancer. Creates a new freelancer with role Physiotherapist
 */
fun Freelancer.Companion.createPhysiotherapist(
    freelancerId: FreelancerId,
    firstName: String,
    lastName: String
): Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(
        FreelancerCreatedEvent(
            freelancerId,
            firstName,
            lastName,
            FreelancerRole.Physiotherapist()
        )
    )

    return freelancer
}

/**
 * Extension method factory for Freelancer. Creates a new freelancer with role Nutritionist
 */
fun Freelancer.Companion.createNutritionist(
    freelancerId: FreelancerId,
    firstName: String,
    lastName: String
): Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(
        FreelancerCreatedEvent(
            freelancerId,
            firstName,
            lastName,
            FreelancerRole.Nutritionist()
        )
    )

    return freelancer
}

/**
 * Extension method factory for Freelancer. Creates a new freelancer with role AthleticTrainer
 */
fun Freelancer.Companion.createAthleticTrainer(
    freelancerId: FreelancerId,
    firstName: String,
    lastName: String
): Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(
        FreelancerCreatedEvent(
            freelancerId,
            firstName,
            lastName,
            FreelancerRole.AthleticTrainer()
        )
    )

    return freelancer
}

/**
 * Extension method factory for Freelancer. Creates a new freelancer with role Biomechanical
 */
fun Freelancer.Companion.createBiomechanical(
    freelancerId: FreelancerId,
    firstName: String,
    lastName: String
): Freelancer {

    val freelancer = Freelancer(freelancerId)
    freelancer.raiseEvent(
        FreelancerCreatedEvent(
            freelancerId,
            firstName,
            lastName,
            FreelancerRole.Biomechanical()
        )
    )

    return freelancer
}
