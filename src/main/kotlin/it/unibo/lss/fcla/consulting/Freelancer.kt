package it.unibo.lss.fcla.consulting

/**
 * Freelancer role as enumeration
 */
sealed class FreelancerRole{
    data class Physiotherapist(val name: String = "Physiotherapist"): FreelancerRole()
    data class AthleticTrainer(val name: String = "AthleticTrainer"): FreelancerRole()
    data class Nutritionist(val name: String = "Nutritionist"): FreelancerRole()
    data class Biomechanical(val name: String = "Biomechanical"): FreelancerRole()
}

/**
 * Represents a freelancer
 */
class Freelancer(val firstName: String, val lastName: String) {

    init {

    }
}