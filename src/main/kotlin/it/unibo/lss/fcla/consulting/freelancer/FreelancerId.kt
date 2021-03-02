package it.unibo.lss.fcla.consulting.freelancer

import it.unibo.lss.fcla.consulting.domain.contracts.AggregateId

/**
 * @author Stefano Braggion
 *
 * Represent an Id for a freelancer in the form of F-00000
 */
data class FreelancerId(val freelancerId: String) : AggregateId {

    private val freelancerIdFormat = Regex("F-[0-9]{5}")

    init {
        require(freelancerId.matches(freelancerIdFormat))
    }

    /**
     * Return the freelancer id string value
     */
    fun value(): String {
        return freelancerId
    }

    /**
     * String representation of freelancer id
     */
    override fun toString(): String {
        return "FreelancerId($freelancerId)"
    }
}
