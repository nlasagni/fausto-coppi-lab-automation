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

/**
 * @author Stefano Braggion
 *
 * [IRequest] representing the update of an existing consulting summary
 */
class UpdateConsultingSummaryRequest(
    val consultingId: ConsultingId,
    val description: String
) : IRequest
