package it.unibo.lss.fcla.consulting.models

/**
 * Freelancer role as enumeration
 */
sealed class FreelancerRole {
    data class Physiotherapist(val name: String = "Physiotherapist") : FreelancerRole()
    data class AthleticTrainer(val name: String = "AthleticTrainer") : FreelancerRole()
    data class Nutritionist(val name: String = "Nutritionist") : FreelancerRole()
    data class Biomechanical(val name: String = "Biomechanical") : FreelancerRole()
}
