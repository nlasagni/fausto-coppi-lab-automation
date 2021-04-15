/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat12

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository

/**
 * Checks all the currently active athletic trainings, refers to requirement FCLAT-12.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class Fclat12CheckActiveAthleticTrainings(
    useCaseOutput: CheckActiveAthleticTrainingsOutput,
    private val repository: ActiveAthleticTrainingRepository
) : CheckActiveAthleticTrainingsInput,
    AthleticTrainingManagement<CheckActiveAthleticTrainingsRequest, Collection<ActiveAthleticTraining>>(useCaseOutput) {

    override fun processRequest(request: CheckActiveAthleticTrainingsRequest): Collection<ActiveAthleticTraining> {
        return repository.findAllByMemberId(MemberId(request.memberId)).map { ActiveAthleticTraining.rehydrate(it) }
    }
}
