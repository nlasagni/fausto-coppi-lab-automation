package it.unibo.lss.fcla.consultingContext.consulting

import it.unibo.lss.fcla.consultingContext.exceptions.ConsultingSummaryDescriptionCannotBeNull
import it.unibo.lss.fcla.consultingContext.exceptions.ConsultingSummaryTypeCannotBeNull
import it.unibo.lss.fcla.consultingContext.freelancer.Freelancer

/**
 * @author Stefano Braggion
 *
 * Entity representing a consulting
 */
class Consulting(val consultingType: String, val description: String, val consultingDate: Date, val freelancer: Freelancer) {

    private var consultingSummary: ConsultingSummary

    init {
        if(description.isEmpty()) throw ConsultingSummaryDescriptionCannotBeNull()
        if(consultingType.isEmpty()) throw ConsultingSummaryTypeCannotBeNull()

        consultingSummary = ConsultingSummary(consultingType, description, consultingDate)
    }
}