package it.unibo.lss.fcla.consulting.presentation.consulting

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.presentation.IRequest
import java.time.LocalDate

/**
 * @author Stefano Braggion
 */

/**
 *
 */
class ReceivePhysiotherapyConsultingRequest(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String
) : IRequest

/**
 *
 */
class ReceiveNutritionistConsultingRequest(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String
) : IRequest

/**
 *
 */
class ReceiveAthleticTrainerConsultingRequest(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String
) : IRequest

/**
 *
 */
class ReceiveBiomechanicalConsultingRequest(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String
) : IRequest

/**
 *
 */
class UpdateConsultingSummaryRequest(
    val consultingId: ConsultingId,
    val description: String
) : IRequest

/**
 *
 */
class ExamineConsultingSummaryRequest(
    val consultingId: ConsultingId
) : IRequest