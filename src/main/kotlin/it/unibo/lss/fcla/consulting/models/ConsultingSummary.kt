package it.unibo.lss.fcla.consulting.models

import it.unibo.lss.fcla.consulting.exceptions.ConsultingException

/**
 * @author Stefano Braggion
 *
 *
 */
class ConsultingSummary(val consultingType: String, val freelancer: Freelancer, val description: String) {

    init {
        if (description.isNullOrEmpty())
            throw ConsultingException("A consulting summary with no description is not valid")
    }
}
