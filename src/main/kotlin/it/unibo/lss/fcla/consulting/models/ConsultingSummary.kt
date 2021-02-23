package it.unibo.lss.fcla.consulting.models

/**
 * @author Stefano Braggion
 *
 * Value object representing a consulting summary
 * with [consultingType], executed by a [freelancer] at a [consultingDate]
 * and containing a [description]
 */
data class ConsultingSummary(val consultingType: String, val description: String, val consultingDate: Date) {

    /**
     * String representation of a consulting summary
     */
    override fun toString(): String {
        return "ConsultingSummary(type=$consultingType, description=$description, at date $consultingDate)"
    }
}
