package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * @author Stefano Braggion
 *
 * Base Exception for consulting context
 */
open class ConsultingException(message: String) : Exception(message)

/**
 * Thrown when a consulting is created without a valid Member Id
 */
class ConsultingMustHaveAValidMember : ConsultingException("A consulting must have a valid member")

/**
 * Thrown when a consulting is created without a valid Consulting Id
 */
class ConsultingMustHaveAValidId : ConsultingException("A consulting must have a valid Id")
