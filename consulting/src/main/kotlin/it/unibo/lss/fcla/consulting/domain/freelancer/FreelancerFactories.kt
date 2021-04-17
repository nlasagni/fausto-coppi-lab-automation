/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

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
