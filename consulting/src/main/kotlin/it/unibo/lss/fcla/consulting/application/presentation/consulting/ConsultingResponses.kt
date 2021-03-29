package it.unibo.lss.fcla.consulting.application.presentation.consulting

import it.unibo.lss.fcla.consulting.application.presentation.IResponse

/**
 * @author Stefano Braggion
 */

/**
 *
 */
class ConsultingErrorResponse(
    val message: String
) : IResponse {

    override fun toString(): String =
        "ConsultingError(${message})"
}
