package it.unibo.lss.fcla.consulting.presentation

import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalTime

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
