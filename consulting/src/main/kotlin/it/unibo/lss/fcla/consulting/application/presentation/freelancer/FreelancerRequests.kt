package it.unibo.lss.fcla.consulting.application.presentation.freelancer

import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 */

/**
 *
 */
class CreateAthleticTrainerFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 *
 */
class CreatePhysiotherapistFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 *
 */
class CreateNutritionistFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 *
 */
class CreateBiomechanicalFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 *
 */
class CreateFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IRequest

/**
 *
 */
class DeleteFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate
) : IRequest

/**
 *
 */
class UpdateFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IRequest

/**
 *
 */
class GetFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate
) : IRequest
