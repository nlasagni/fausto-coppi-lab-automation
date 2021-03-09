package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * @author Stefano Braggion
 *
 * Base Exception for consulting context
 */
open class ConsultingException(message: String) : Exception(message)

/**
 *
 */
class ConsultingMustHaveAValidMember : ConsultingException("A consulting must have a valid member")

/**
 *
 */
class ConsultingMustHaveAValidId : ConsultingException("A consulting must have a valid Id")
