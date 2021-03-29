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
 * Represents a textual response message
 */
class MessageResponse(
    val message: String
) : IResponse

/**
 *
 */
class FreelancerResponse(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) : IResponse

/**
 *
 */
class FreelancerAvailabilityResponse(
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IResponse
