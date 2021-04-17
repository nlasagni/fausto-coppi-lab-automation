/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat1

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import java.time.LocalDate

/**
 * Class that represents the request coming from outer layer of
 * planning a new athletic training.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class PlanActiveAthleticTrainingRequest(
    val athleticTrainerId: AthleticTrainerId,
    val memberId: MemberId,
    val purpose: String,
    val startDay: LocalDate,
    val endDay: LocalDate
)
