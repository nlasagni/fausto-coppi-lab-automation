package it.unibo.lss.fcla.consultingContext.consulting

/**
 * @author Stefano Braggion
 *
 * Value object representing a consulting id
 */
data class ConsultingId(val consultingId: String) {

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
