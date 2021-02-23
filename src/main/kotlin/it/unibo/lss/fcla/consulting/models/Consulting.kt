package it.unibo.lss.fcla.consulting.models

/**
 * @author Stefano Braggion
 *
 * Entity representing a consulting
 */
class Consulting(val consultingType: String, val description: String, val consultingDate: Date, val freelancer: Freelancer) {

    private lateinit var consultingSummary: ConsultingSummary

    init {
        require(!description.isNullOrEmpty())
        require(!consultingType.isNullOrEmpty())

        consultingSummary = ConsultingSummary(consultingType, description, consultingDate)
    }
}