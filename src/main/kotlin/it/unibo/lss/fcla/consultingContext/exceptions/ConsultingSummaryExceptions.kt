package it.unibo.lss.fcla.consultingContext.exceptions

/**
 * Exception that occur when a consulting summary is created without a type
 */
class ConsultingSummaryTypeCannotBeNull : Exception("A consulting summary must have a type")

/**
 * Exception that occur when a consulting summary is created without a description
 */
class ConsultingSummaryDescriptionCannotBeNull : Exception("A consulting summary must have a description")