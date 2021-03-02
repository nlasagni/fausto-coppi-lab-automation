package it.unibo.lss.fcla.consulting.domain.member

/**
 * @author Stefano Braggion
 *
 * Represent the Id of a member
 */
data class MemberId(val memberId: String) {
    private val memberIdFormat = Regex("M-[0-9]{5}")

    init {
        require(memberId.matches(memberIdFormat))
    }

    /**
     * Return the member id string value
     */
    fun value(): String {
        return memberId
    }

    /**
     * String representation of member id
     */
    override fun toString(): String {
        return "MemberId($memberId)"
    }
}

