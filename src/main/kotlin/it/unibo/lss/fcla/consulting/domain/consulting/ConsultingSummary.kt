package it.unibo.lss.fcla.consulting.domain.consulting

/**
 * @author Stefano Braggion
 *
 * Value object representing a consulting summary
 * with a [consultingType] and containing a [description]
 */
data class ConsultingSummary(val consultingType: ConsultingType, val description: String) {

    /**
     * String representation of a consulting summary
     */
    override fun toString(): String {
        return "ConsultingSummary(type=$consultingType, description=$description)"
    }
}
