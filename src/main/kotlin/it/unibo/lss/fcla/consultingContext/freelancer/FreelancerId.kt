package it.unibo.lss.fcla.consultingContext.freelancer

data class FreelancerId(val freelancerId: String) {

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