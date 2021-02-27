package it.unibo.lss.fcla.consultingContext.domain.exceptions

/**
 * Exception that occur when a consulting summary is created without a type
 */
class ConsultingSummaryTypeCannotBeNull : ConsultingException("A consulting summary must have a type")

/**
 * Exception that occur when a consulting summary is created without a description
 */
class ConsultingSummaryDescriptionCannotBeNull : ConsultingException("A consulting summary must have a description")
