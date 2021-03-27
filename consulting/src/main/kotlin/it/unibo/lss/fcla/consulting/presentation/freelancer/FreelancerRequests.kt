package it.unibo.lss.fcla.consulting.presentation.freelancer

import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.presentation.IRequest
import java.time.LocalTime

/**
 * @author Stefano Braggion
 */

/**
 *
 */
class CreateFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: Date,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IRequest

/**
 *
 */
class DeleteFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: Date
) : IRequest

/**
 *
 */
class UpdateFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: Date,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IRequest

/**
 *
 */
class GetFreelancerAvailabilityForDayRequest(
    val availabilityDate: Date
) : IRequest