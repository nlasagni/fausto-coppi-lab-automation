package it.unibo.lss.fcla.consulting.application.presentation.freelancer

import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 */

/**
 * [IRequest] representing the creation of a freelancer of type Athletic Trainer
 */
class CreateAthleticTrainerFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 * [IRequest] representing the creation of a freelancer of type Physiotherapist
 */
class CreatePhysiotherapistFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 * [IRequest] representing the creation of a freelancer of type Nutritionist
 */
class CreateNutritionistFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 * [IRequest] representing the creation of a freelancer of type Biomechanical
 */
class CreateBiomechanicalFreelancerRequest(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String
) : IRequest

/**
 * [IRequest] representing the creation of a freelancer availability
 */
class CreateFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IRequest

/**
 * [IRequest] representing the deletion of a freelancer availability
 */
class DeleteFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate
) : IRequest

/**
 * [IRequest] representing the update of a freelancer availability
 */
class UpdateFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IRequest

/**
 * [IRequest] representing the retrieving of a freelancer availability
 */
class GetFreelancerAvailabilityForDayRequest(
    val freelancerId: FreelancerId,
    val availabilityDate: LocalDate
) : IRequest
