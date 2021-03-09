package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * Exception that occur when a consulting summary is created without a description
 */
class ConsultingSummaryDescriptionCannotBeEmpty :
    ConsultingException("A consulting summary must have a description")

/**
 *
 */
class ConsultingSummaryMustHaveAValidFreelancer :
    ConsultingException("A consulting summary must have a valid freelancer")
