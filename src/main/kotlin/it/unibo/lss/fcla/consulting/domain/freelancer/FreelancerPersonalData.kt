package it.unibo.lss.fcla.consulting.domain.freelancer

import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerFirstNameCannotBeNull
import it.unibo.lss.fcla.consulting.domain.exceptions.FreelancerLastNameCannotBeNull

/**
 * @author Stefano Braggion
 *
 * Value object representing personal data such as [firstName], [lastName]
 * and [role] of a [Freelancer]
 */
data class FreelancerPersonalData(
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) {

    /**
     * Check invariants
     */
    init {
        if (firstName.isEmpty()) {
            throw FreelancerFirstNameCannotBeNull()
        }
        if (lastName.isEmpty()) {
            throw FreelancerLastNameCannotBeNull()
        }
    }
}
