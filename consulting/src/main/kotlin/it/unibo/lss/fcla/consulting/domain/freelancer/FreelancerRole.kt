/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.freelancer

/**
 * Freelancer role as enumeration
 */
sealed class FreelancerRole {
    data class Physiotherapist(val name: String = "Physiotherapist") : FreelancerRole()
    data class AthleticTrainer(val name: String = "AthleticTrainer") : FreelancerRole()
    data class Nutritionist(val name: String = "Nutritionist") : FreelancerRole()
    data class Biomechanical(val name: String = "Biomechanical") : FreelancerRole()
}
