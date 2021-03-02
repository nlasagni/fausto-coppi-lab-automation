package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.domain.contracts.AggregateId

/**
 * @author Stefano Braggion
 *
 * Represent an Id for a consulting in the form of CS-00000
 */
data class ConsultingId(val consultingId: String) : AggregateId {

    private val consultingIdFormat = Regex("CS-[0-9]{5}")

    init {
        require(consultingId.matches(consultingIdFormat))
    }

    /**
     * Return the consulting id string value
     */
    fun value(): String {
        return consultingId
    }

    /**
     * String representation of consulting id
     */
    override fun toString(): String {
        return "ConsultingId($consultingId)"
    }
}
