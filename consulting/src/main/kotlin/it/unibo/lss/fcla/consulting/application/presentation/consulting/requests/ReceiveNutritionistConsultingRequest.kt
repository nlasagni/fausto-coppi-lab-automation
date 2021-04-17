/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.presentation.consulting.requests

import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalDate

/**
 * @author Stefano Braggion
 *
 * [IRequest] representing the creation of a new consulting of type Nutritionist
 */
class ReceiveNutritionistConsultingRequest(
    val consultingId: ConsultingId,
    val memberId: MemberId,
    val consultingDate: LocalDate,
    val freelancerId: FreelancerId,
    val description: String
) : IRequest
