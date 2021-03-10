package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingSummaryMustHaveAValidFreelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 * Value object representing a consulting summary
 * with a [consultingType] and containing a [description], executed by a
 * freelancer identified with [freelancerId] in a specific [consultingDate]
 */
data class ConsultingSummary(
    val consultingDate: Date,
    val freelancerId: FreelancerId,
    val consultingType: ConsultingType,
    val description: String
) {

    init {
        if (description.isEmpty()) {
            throw ConsultingSummaryDescriptionCannotBeEmpty()
        }
        if (freelancerId.isEmpty()) {
            throw ConsultingSummaryMustHaveAValidFreelancer()
        }
    }

    /**
     * String representation of a consulting summary
     */
    override fun toString(): String {
        return "ConsultingSummary(type=$consultingType, description=$description)"
    }
}
