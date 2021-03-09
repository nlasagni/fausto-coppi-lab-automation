package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerLastNameCannotBeNull

/**
 *
 */
data class FreelancerPersonalData(
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) {

    init {
        if (firstName.isEmpty()) {
            throw FreelancerFirstNameCannotBeNull()
        }
        if (lastName.isEmpty()) {
            throw FreelancerLastNameCannotBeNull()
        }
    }
}
