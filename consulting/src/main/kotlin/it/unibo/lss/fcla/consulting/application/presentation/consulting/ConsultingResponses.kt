package it.unibo.lss.fcla.consulting.application.presentation.consulting

import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalDate

/**
 * @author Stefano Braggion
 */

class ConsultingResponse(
    val consultingId: ConsultingId,
    val memberId: MemberId,
    val consultingDate: LocalDate,
    val freelancerId: FreelancerId,
    val consultingType: ConsultingType,
    val description: String
) : IResponse {

    /**
     * String representation of the [ConsultingResponse]
     */
    override fun toString(): String =
        "Consulting(Id=${consultingId}, Member=${memberId}, Date=${consultingDate}," +
                " Freelancer=${freelancerId}, Type=${consultingType}) \n" +
                "Summary(${description})"
}

/**
 * Represent a consulting error response
 */
class ConsultingErrorResponse(
    val message: String
) : IResponse {

    /**
     * String representation of the [ConsultingErrorResponse]
     */
    override fun toString(): String =
        "ConsultingError(${message})"
}
