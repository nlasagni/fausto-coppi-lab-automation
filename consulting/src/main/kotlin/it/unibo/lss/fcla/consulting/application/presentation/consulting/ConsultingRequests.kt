package it.unibo.lss.fcla.consulting.application.presentation.consulting

import it.unibo.lss.fcla.consulting.application.presentation.IRequest

/**
 * @author Stefano Braggion
 */

/**
 *
 */
class ReceivePhysiotherapyConsultingRequest(
    /* consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String */
) : IRequest

/**
 *
 */
class ReceiveNutritionistConsultingRequest(
    /* consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String */
) : IRequest

/**
 *
 */
class ReceiveAthleticTrainerConsultingRequest(
    /* consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String */
) : IRequest

/**
 *
 */
class ReceiveBiomechanicalConsultingRequest(
    /* consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: LocalDate,
    freelancerId: FreelancerId,
    description: String */
) : IRequest

/**
 *
 */
class UpdateConsultingSummaryRequest(
    /* val consultingId: ConsultingId, */
    val description: String
) : IRequest

/**
 *
 */
class ExamineConsultingSummaryRequest(
    /* val consultingId: ConsultingId */
) : IRequest
