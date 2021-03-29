package it.unibo.lss.fcla.consulting.application.presentation.freelancer

import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 */

/**
 * Represents a generic textual response message
 */
class MessageResponse(
    val message: String
) : IResponse

/**
 * Represent a freelancer response object
 */
class FreelancerResponse(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : IResponse {

    /**
     * String representation of the [FreelancerResponse]
     */
    override fun toString(): String =
        "FreelancerResponse(Id=${freelancerId}, Firstname=${firstName}, Lastname=${lastName}, Role=${role})"
}

/**
 * Represent a freelancer availability response
 */
class FreelancerAvailabilityResponse(
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IResponse {

    /**
     * String representation of the [FreelancerAvailabilityResponse]
     */
    override fun toString(): String =
        "FreelancerAvailabilityResponse(On=${availabilityDate}, From=${fromTime}, To=${toTime})"
}

/**
 * Represent a freelancer error response
 */
class FreelancerErrorResponse(
    val message: String
) : IResponse {

    /**
     * String representation of the [FreelancerErrorResponse]
     */
    override fun toString(): String =
        "FreelancerError(${message})"
}
