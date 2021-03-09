package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * Thrown when a consulting summary is created without a description
 */
class ConsultingSummaryDescriptionCannotBeEmpty :
    ConsultingException("A consulting summary must have a description")

/**
 * Thrown when a consulting summary is created without a valid Freelancer Id
 */
class ConsultingSummaryMustHaveAValidFreelancer :
    ConsultingException("A consulting summary must have a valid freelancer")
