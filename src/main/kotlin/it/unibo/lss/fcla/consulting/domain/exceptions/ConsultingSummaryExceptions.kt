package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * Exception that occur when a consulting summary is created without a type
 */
class ConsultingSummaryTypeCannotBeEmpty : ConsultingException("A consulting summary must have a type")

/**
 * Exception that occur when a consulting summary is created without a description
 */
class ConsultingSummaryDescriptionCannotBeEmpty : ConsultingException("A consulting summary must have a description")

/**
 * Exception that occur when a consulting is created with an invalid freelancer
 */
class ConsultingSummaryWithInvalidFreelancer : ConsultingException("A consulting must have a valid freelancer")